package com.crowdproj.resources.app.controller

import com.crowdproj.resources.api.v1.models.*
import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.common.models.ResourcesCommand
import com.crowdproj.resources.logging.common.IMpLogWrapper
import io.ktor.server.application.*

suspend fun ApplicationCall.createResource(
    appSettings: ResourceAppSettings,
    loggerProductProperty: IMpLogWrapper
) = processV1<ResourceCreateRequest, ResourceCreateResponse>(
    appSettings,
    loggerProductProperty,
    "resource-create",
    ResourcesCommand.CREATE
)

suspend fun ApplicationCall.readResource(
    appSettings: ResourceAppSettings,
    loggerProductProperty: IMpLogWrapper
) = processV1<ResourceReadRequest, ResourceReadResponse>(
    appSettings,
    loggerProductProperty,
    "resource-read",
    ResourcesCommand.READ
)

suspend fun ApplicationCall.updateResource(
    appSettings: ResourceAppSettings,
    loggerProductProperty: IMpLogWrapper
) = processV1<ResourceUpdateRequest, ResourceUpdateResponse>(
    appSettings,
    loggerProductProperty,
    "resource-update",
    ResourcesCommand.UPDATE
)

suspend fun ApplicationCall.deleteResource(
    appSettings: ResourceAppSettings,
    loggerProductProperty: IMpLogWrapper
) = processV1<ResourceDeleteRequest, ResourceDeleteResponse>(
    appSettings,
    loggerProductProperty,
    "resource-delete",
    ResourcesCommand.DELETE
)

suspend fun ApplicationCall.searchResource(
    appSettings: ResourceAppSettings,
    loggerProductProperty: IMpLogWrapper
) = processV1<ResourceSearchRequest, ResourceSearchResponse>(
    appSettings,
    loggerProductProperty,
    "product-property-search",
    ResourcesCommand.SEARCH
)