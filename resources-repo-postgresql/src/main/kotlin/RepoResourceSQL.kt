package com.crowdproj.resources.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import com.crowdproj.resources.common.helpers.asResourcesError
import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.common.repo.*

class RepoResourceSQL(
    properties: SqlProperties,
    initObjects: Collection<Resources> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IResourceRepository {
    private val rsTable = ResourcesTable(properties.table)

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )


    init {
        transaction(conn) {
            SchemaUtils.create(rsTable)
            initObjects.forEach { createResource(it) }
        }
    }

    private fun createResource(rs: Resources): Resources {
        val res = rsTable
            .insert {
                to(it, rs, randomUuid)
            }
            .resultedValues
            ?.map { rsTable.from(it) }
        return res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction(conn) {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbResourceResponse): DbResourceResponse =
        transactionWrapper(block) { DbResourceResponse.error(it.asResourcesError()) }

    override suspend fun createResource(rq: DbResourceRequest): DbResourceResponse = transactionWrapper {
        DbResourceResponse.success(createResource(rq.resources))
    }

    private fun read(id: ResourcesId): DbResourceResponse {
        val res = rsTable.select {
            rsTable.id eq id.asString()
        }.singleOrNull() ?: return DbResourceResponse.errorNotFound
        return DbResourceResponse.success(rsTable.from(res))
    }

    override suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: ResourcesId,
        lock: ResourcesLock,
        block: (Resources) -> DbResourceResponse
    ): DbResourceResponse =
        transactionWrapper {
            if (id == ResourcesId.NONE) return@transactionWrapper DbResourceResponse.errorEmptyId

            val current = rsTable.select { rsTable.id eq id.asString() }
                .singleOrNull()
                ?.let { rsTable.from(it) }

            when {
                current == null -> DbResourceResponse.errorNotFound
                current.lock != lock -> DbResourceResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }


    override suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse = update(rq.resources.id, rq.resources.lock) {
        rsTable.update({ rsTable.id eq rq.resources.id.asString() }) {
            to(it, rq.resources.copy(lock = ResourcesLock(randomUuid())), randomUuid)
        }
        read(rq.resources.id)
    }

    override suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse = update(rq.id, rq.lock) {
        rsTable.deleteWhere { id eq rq.id.asString() }
        DbResourceResponse.success(it)
    }

    override suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse =
        transactionWrapper({
            val res = rsTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != ResourcesUserId.NONE) {
                        add(rsTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.titleFilter.isNotBlank()) {
                        add(
                            (rsTable.resourcesId like "%${rq.titleFilter}%")
                                    or (rsTable.resourcesId like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbResourcesResponse(data = res.map { rsTable.from(it) }, isSuccess = true)
        }, {
            DbResourcesResponse.error(it.asResourcesError())
        })
}
