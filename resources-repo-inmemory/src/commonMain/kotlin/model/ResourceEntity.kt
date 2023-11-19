package com.crowdproj.resources.repo.inmemory.model

import com.crowdproj.resources.common.models.*
data class ResourceEntity(
    val id: String? = null,
    val resourcesId: String? = null,
    val scheduleId: String? = null,
    val ownerId: String? = null,
    val visible: String? = null,
    val lock: String? = null,
) {
    constructor(model: Resources): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        resourcesId = model.resourcesId.takeIf { it != OtherResourcesId.NONE }?.asString(),
        scheduleId = model.scheduleId.takeIf { it != ScheduleId.NONE }?.asString(),
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        visible = model.visible.takeIf { it != ResourcesVisible.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = Resources(
        id = id?.let { ResourcesId(it) }?: ResourcesId.NONE,
        resourcesId = resourcesId?.let { OtherResourcesId(it) }?: OtherResourcesId.NONE,
        scheduleId = scheduleId?.let { ScheduleId(it) }?: ScheduleId.NONE,
        ownerId = ownerId?.let { ResourcesUserId(it) }?: ResourcesUserId.NONE,
        visible = visible?.let { ResourcesVisible.valueOf(it) }?: ResourcesVisible.NONE,
        lock = lock?.let { ResourcesLock(it) } ?: ResourcesLock.NONE,
    )
}