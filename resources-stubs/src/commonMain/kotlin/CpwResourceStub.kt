package com.crowdproj.resources.stubs

import com.crowdproj.resources.common.models.*

object CpwResourceStub {
    fun get() = Resources(
        id = ResourcesId("666"),
        resourcesId = OtherResourcesId("1111"),
        scheduleId = ScheduleId("2222"),
        ownerId = ResourcesUserId("user-1"),
        visible = ResourcesVisible.VISIBLE_PUBLIC,
        permissionsClient = mutableSetOf(
            ResourcesPermissionClient.READ,
            ResourcesPermissionClient.UPDATE,
            ResourcesPermissionClient.DELETE,
            ResourcesPermissionClient.MAKE_VISIBLE_PUBLIC,
            ResourcesPermissionClient.MAKE_VISIBLE_GROUP,
            ResourcesPermissionClient.MAKE_VISIBLE_OWNER,
        )
    )

    fun prepareResult(block: Resources.() -> Unit): Resources = get().apply(block)

    fun prepareSearchList(id: String) = listOf(
        resoucesDemand("d-666-01"),
        resoucesDemand("d-666-02"),
        resoucesDemand("d-666-03"),
        resoucesDemand("d-666-04"),
        resoucesDemand("d-666-05"),
        resoucesDemand("d-666-06"),
    )

    private fun resoucesDemand(id: String) =  resource(get(), id = id)

    private fun resource(base: Resources, id: String) = base.copy(
        id = ResourcesId(id),
    )

}