package com.crowdproj.resources.auth

import com.crowdproj.resources.common.permissions.ResourcesPermissionClient
import com.crowdproj.resources.common.permissions.ResourcesPrincipalRelations
import com.crowdproj.resources.common.permissions.ResourcesUserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<ResourcesUserPermissions>,
    relations: Iterable<ResourcesPrincipalRelations>,
) = mutableSetOf<ResourcesPermissionClient>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    ResourcesUserPermissions.READ_OWN to mapOf(
        ResourcesPrincipalRelations.OWN to ResourcesPermissionClient.READ
    ),
    ResourcesUserPermissions.READ_PUBLIC to mapOf(
        ResourcesPrincipalRelations.PUBLIC to ResourcesPermissionClient.READ
    ),


    // UPDATE
    ResourcesUserPermissions.UPDATE_OWN to mapOf(
        ResourcesPrincipalRelations.OWN to ResourcesPermissionClient.UPDATE
    ),
    ResourcesUserPermissions.UPDATE_PUBLIC to mapOf(
        ResourcesPrincipalRelations.PUBLIC to ResourcesPermissionClient.UPDATE
    ),

    // DELETE
    ResourcesUserPermissions.DELETE_OWN to mapOf(
        ResourcesPrincipalRelations.OWN to ResourcesPermissionClient.DELETE
    ),
    ResourcesUserPermissions.DELETE_PUBLIC to mapOf(
        ResourcesPrincipalRelations.PUBLIC to ResourcesPermissionClient.DELETE
    ),

    // SEARCH
    ResourcesUserPermissions.SEARCH_OWN to mapOf(
        ResourcesPrincipalRelations.OWN to ResourcesPermissionClient.SEARCH
    ),
    ResourcesUserPermissions.SEARCH_PUBLIC to mapOf(
        ResourcesPrincipalRelations.PUBLIC to ResourcesPermissionClient.SEARCH
    ),
)