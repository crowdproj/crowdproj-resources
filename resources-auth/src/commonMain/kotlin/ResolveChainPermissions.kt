package com.crowdproj.resources.auth

import com.crowdproj.resources.common.permissions.ResourcesUserGroups
import com.crowdproj.resources.common.permissions.ResourcesUserPermissions

fun resolveChainPermissions(
    groups: Iterable<ResourcesUserGroups>,
) = mutableSetOf<ResourcesUserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    ResourcesUserGroups.USER to setOf(
        ResourcesUserPermissions.READ_OWN,
        ResourcesUserPermissions.READ_PUBLIC,
        ResourcesUserPermissions.CREATE_OWN,
        ResourcesUserPermissions.UPDATE_OWN,
        ResourcesUserPermissions.DELETE_OWN,
        ResourcesUserPermissions.SEARCH_OWN,
        ResourcesUserPermissions.SEARCH_PUBLIC,
    ),
    ResourcesUserGroups.ADMIN_MP to ResourcesUserPermissions.values().toSet(),
    ResourcesUserGroups.TEST to setOf(),
    ResourcesUserGroups.BAN_MP to setOf(),
)

private val groupPermissionsDenys = mapOf(
    ResourcesUserGroups.USER to setOf(),
    ResourcesUserGroups.ADMIN_MP to setOf(),
    ResourcesUserGroups.TEST to setOf(),
    ResourcesUserGroups.BAN_MP to setOf(
        ResourcesUserPermissions.UPDATE_OWN,
        ResourcesUserPermissions.CREATE_OWN,
    ),
)