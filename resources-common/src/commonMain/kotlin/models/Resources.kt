package com.crowdproj.resources.common.models

import com.crowdproj.resources.common.permissions.ResourcesPrincipalRelations
import com.crowdproj.resources.common.permissions.ResourcesPermissionClient

data class Resources(
    var id: ResourcesId = ResourcesId.NONE,
    var resourcesId: OtherResourcesId = OtherResourcesId.NONE,
    var scheduleId: ScheduleId = ScheduleId.NONE,
    var ownerId: ResourcesUserId = ResourcesUserId.NONE,
    var deleted: Boolean = false,
    var visible: ResourcesVisible = ResourcesVisible.NONE,
    var lock: ResourcesLock = ResourcesLock.NONE,
    var principalRelations: Set<ResourcesPrincipalRelations> = emptySet(),
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