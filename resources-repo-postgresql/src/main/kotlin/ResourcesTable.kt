package com.crowdproj.resources.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import com.crowdproj.resources.common.models.*

class ResourcesTable(tableName: String = "resources") : Table(tableName) {
    val id = varchar("id", 128)
    val resourcesId = text("resourcesId" )
    val scheduleId = text("scheduleId")
    val owner = varchar("owner", 128)
    val visible = enumeration("visible", ResourcesVisible::class)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res : ResultRow) = Resources(
        id = ResourcesId(res[id].toString()),
        resourcesId = OtherResourcesId(res[resourcesId].toString()),
        scheduleId = ScheduleId(res[scheduleId].toString()),
        ownerId = ResourcesUserId(res[owner].toString()),
        visible = res[visible],
        lock = ResourcesLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, rs: Resources, randomUuid: () -> String) {
        it[id] = rs.id.takeIf { it != ResourcesId.NONE }?.asString() ?: randomUuid()
        it[resourcesId] = rs.resourcesId.asString()
        it[scheduleId] = rs.scheduleId.asString()
        it[owner] = rs.ownerId.asString()
        it[visible] = rs.visible
        it[lock] = rs.lock.takeIf { it != ResourcesLock.NONE }?.asString() ?: randomUuid()
    }

}