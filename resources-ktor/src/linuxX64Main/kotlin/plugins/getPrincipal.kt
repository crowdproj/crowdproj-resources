package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.common.models.ResourcesUserId
import com.crowdproj.resources.common.permissions.ResourcesPrincipalModel
import com.crowdproj.resources.common.permissions.ResourcesUserGroups
import io.ktor.server.application.*

actual fun ApplicationCall.getPrincipal(appSettings: ResourceAppSettings) = ResourcesPrincipalModel(
    id = ResourcesUserId("user-1"),
    fname = "User",
    mname = "Userovich",
    lname = "Userov",
    groups = setOf(ResourcesUserGroups.TEST, ResourcesUserGroups.USER),
)