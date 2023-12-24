package com.crowdproj.resources.repo.inmemory

import com.benasher44.uuid.uuid4
import com.crowdproj.resources.common.helpers.errorRepoConcurrency
import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.common.repo.*
import io.github.reactivecircus.cache4k.Cache
import com.crowdproj.resources.repo.inmemory.model.ResourceEntity
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class ResourcesRepoInMemory(
    initObjects: List<Resources> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IResourceRepository {

    private val cache = Cache.Builder<String, ResourceEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(rs: Resources) {
        val entity = ResourceEntity(rs)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createResource(rq: DbResourceRequest): DbResourceResponse {
        val key = randomUuid()
        val rs = rq.resources.copy(id = ResourcesId(key), lock = ResourcesLock(randomUuid()))
        val entity = ResourceEntity(rs)
        cache.put(key, entity)
        return DbResourceResponse(
            data = rs,
            isSuccess = true,
        )
    }

    override suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse {
        val key = rq.id.takeIf { it != ResourcesId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbResourceResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse {
        val key = rq.resources.id.takeIf { it != ResourcesId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.resources.lock.takeIf { it != ResourcesLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newRs = rq.resources.copy(lock = ResourcesLock(randomUuid()))
        val entity = ResourceEntity(newRs)
        return mutex.withLock {
            val oldRs = cache.get(key)
            when {
                oldRs == null -> resultErrorNotFound
                oldRs.lock != oldLock -> DbResourceResponse(
                    data = oldRs.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(ResourcesLock(oldLock), oldRs.lock?.let { ResourcesLock(it) }))
                )

                else -> {
                    cache.put(key, entity)
                    DbResourceResponse(
                        data = newRs,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse {
        val key = rq.id.takeIf { it != ResourcesId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != ResourcesLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldRs = cache.get(key)
            when {
                oldRs == null -> resultErrorNotFound
                oldRs.lock != oldLock -> DbResourceResponse(
                    data = oldRs.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(ResourcesLock(oldLock), oldRs.lock?.let { ResourcesLock(it) }))
                )

                else -> {
                    cache.invalidate(key)
                    DbResourceResponse(
                        data = oldRs.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != ResourcesUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.resourcesId?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbResourcesResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbResourceResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                ResourcesError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbResourceResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                ResourcesError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbResourceResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                ResourcesError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
