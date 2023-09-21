package com.crowdproj.resources.common.models

data class Resources(
    var id: ResourcesId = ResourcesId.NONE,
    var lock: ResourcesLock = ResourcesLock.NONE,
    var resourcesId: OtherResourcesId = OtherResourcesId.NONE,
    var scheduleId: ScheduleId = ScheduleId.NONE,
    var ownerId: ResourcesUserId = ResourcesUserId.NONE,
    var deleted: Boolean = false,
    var visible: ResourcesVisible = ResourcesVisible.NONE,
    val permissionsClient: MutableSet<ResourcesPermissionClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE
    fun deepCopy(): Resources = copy(
        permissionsClient = permissionsClient.toMutableSet()
    )

    companion object {
        private val NONE = Resources()
    }
}