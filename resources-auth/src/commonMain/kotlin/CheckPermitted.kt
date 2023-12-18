package com.crowdproj.resources.auth

import com.crowdproj.resources.common.models.ResourcesCommand
import com.crowdproj.resources.common.permissions.ResourcesPrincipalRelations
import com.crowdproj.resources.common.permissions.ResourcesUserPermissions

fun checkPermitted(
    command: ResourcesCommand,
    relations: Iterable<ResourcesPrincipalRelations>,
    permissions: Iterable<ResourcesUserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] != null
    }

private data class AccessTableConditions(
    val command: ResourcesCommand,
    val permission: ResourcesUserPermissions,
    val relation: ResourcesPrincipalRelations
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = ResourcesCommand.CREATE,
        permission = ResourcesUserPermissions.CREATE_OWN,
        relation = ResourcesPrincipalRelations.NEW,
    ) to true,

    // Read
    AccessTableConditions(
        command = ResourcesCommand.READ,
        permission = ResourcesUserPermissions.READ_OWN,
        relation = ResourcesPrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = ResourcesCommand.READ,
        permission = ResourcesUserPermissions.READ_PUBLIC,
        relation = ResourcesPrincipalRelations.PUBLIC,
    ) to true,

    // Update
    AccessTableConditions(
        command = ResourcesCommand.UPDATE,
        permission = ResourcesUserPermissions.UPDATE_OWN,
        relation = ResourcesPrincipalRelations.OWN,
    ) to true,

    AccessTableConditions(
        command = ResourcesCommand.UPDATE,
        permission = ResourcesUserPermissions.UPDATE_PUBLIC,
        relation = ResourcesPrincipalRelations.PUBLIC,
    ) to true,

    // Delete
    AccessTableConditions(
        command = ResourcesCommand.DELETE,
        permission = ResourcesUserPermissions.DELETE_OWN,
        relation = ResourcesPrincipalRelations.OWN,
    ) to true,

    AccessTableConditions(
        command = ResourcesCommand.DELETE,
        permission = ResourcesUserPermissions.DELETE_PUBLIC,
        relation = ResourcesPrincipalRelations.PUBLIC,
    ) to true,

    // Search
    AccessTableConditions(
        command = ResourcesCommand.SEARCH,
        permission = ResourcesUserPermissions.SEARCH_OWN,
        relation = ResourcesPrincipalRelations.OWN,
    ) to true,

    AccessTableConditions(
        command = ResourcesCommand.SEARCH,
        permission = ResourcesUserPermissions.SEARCH_PUBLIC,
        relation = ResourcesPrincipalRelations.PUBLIC,
    ) to true,
)