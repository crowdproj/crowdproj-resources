package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.models.*

abstract class BaseInitResources(val op: String): IInitObjects<Resources> {

    open val lockOld: ResourcesLock = ResourcesLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: ResourcesLock = ResourcesLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        resourcesId: OtherResourcesId = OtherResourcesId("1111"),
        scheduleId: ScheduleId = ScheduleId("2222"),
        ownerId: ResourcesUserId = ResourcesUserId("owner-123"),
        lock: ResourcesLock = lockOld,
    ) = Resources(
        id = ResourcesId("resource-repo-$op-$suf"),
        resourcesId = resourcesId,
        scheduleId = scheduleId,
        ownerId = ownerId,
        visible = ResourcesVisible.VISIBLE_TO_OWNER,
        lock = lock,
    )
}