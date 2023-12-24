package com.crowdproj.resources.common.permissions

import com.crowdproj.resources.common.models.ResourcesUserId

data class ResourcesPrincipalModel(
    val id: ResourcesUserId = ResourcesUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<ResourcesUserGroups> = emptySet()
) {
    companion object {
        val NONE = ResourcesPrincipalModel()
    }
}