package com.crowdproj.resources.mappers.v1

import com.crowdproj.resources.api.v1.models.*
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.mappers.v1.exceptions.UnknownMkplCommand


fun ResourcesContext.toApi(): IResponseResource = when (command) {
    ResourcesCommand.CREATE -> toApiCreate()
    ResourcesCommand.READ -> toApiRead()
    ResourcesCommand.UPDATE -> toApiUpdate()
    ResourcesCommand.DELETE -> toApiDelete()
    ResourcesCommand.SEARCH -> toApiSearch()
    ResourcesCommand.NONE -> throw UnknownMkplCommand(command)
    else -> throw UnknownMkplCommand(command)
}

fun ResourcesContext.toApiCreate() = ResourceCreateResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    resource = toApiResources(this.resourceResponse),
)

fun ResourcesContext.toApiRead() = ResourceReadResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    resource = toApiResources(this.resourceResponse),
)

fun ResourcesContext.toApiUpdate() = ResourceUpdateResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    resource = toApiResources(this.resourceResponse),
)

fun ResourcesContext.toApiDelete() = ResourceDeleteResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    resource = toApiResources(this.resourceResponse),
)

fun ResourcesContext.toApiSearch() = ResourceSearchResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    resources = resourcesResponse.mapNotNull { toApiResources(it) }.takeIf { it.isNotEmpty() },
)

fun ResourcesContext.toTransportInit() = ResourceInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = toApiResult(),
    errors = toApiErrors(),
)

private fun toApiResources(resource: Resources): ResourceResponseObject? = if (resource.isEmpty()) {
    null
} else {
    ResourceResponseObject(
        id = resource.id.takeIf { it != ResourcesId.NONE }?.asString(),
        resourceId = resource.resourcesId.takeIf { it != OtherResourcesId.NONE }?.asString(),
        scheduleId = resource.scheduleId.takeIf { it != ScheduleId.NONE }?.asString(),
        ownerId = resource.ownerId.takeIf { it != ResourcesUserId.NONE }?.asString(),
        visible = resource.visible.toApiResourcesVisibility(),
        lock = resource.lock.takeIf { it != ResourcesLock.NONE }?.asString(),
        //permissions = resource.permissionsClient.toApiPermissions(),
    )
}

private fun Set<ResourcesPermissionClient>.toApiPermissions(): Set<ResourcePermissions>? = this
    .map { it.toApiPermission() }
    .takeIf { it.isNotEmpty() }
    ?.toSet()

private fun ResourcesPermissionClient.toApiPermission(): ResourcePermissions = when(this) {
    ResourcesPermissionClient.READ -> ResourcePermissions.READ
    ResourcesPermissionClient.UPDATE -> ResourcePermissions.UPDATE
    ResourcesPermissionClient.DELETE -> ResourcePermissions.DELETE
    ResourcesPermissionClient.MAKE_VISIBLE_PUBLIC -> ResourcePermissions.MAKE_VISIBLE_PUBLIC
    ResourcesPermissionClient.MAKE_VISIBLE_GROUP -> ResourcePermissions.MAKE_VISIBLE_GROUP
    ResourcesPermissionClient.MAKE_VISIBLE_OWNER -> ResourcePermissions.MAKE_VISIBLE_OWN
}

private fun ResourcesVisible.toApiResourcesVisibility(): ResourceVisibility? = when(this) {
    ResourcesVisible.NONE -> null
    ResourcesVisible.VISIBLE_TO_OWNER -> ResourceVisibility.OWNER_ONLY
    ResourcesVisible.VISIBLE_TO_GROUP -> ResourceVisibility.REGISTERED_ONLY
    ResourcesVisible.VISIBLE_PUBLIC -> ResourceVisibility.PUBLIC
}

private fun ResourcesContext.toApiErrors(): List<Error>? = errors.map { it.toApiError() }.takeIf { it.isNotEmpty() }
private fun ResourcesError.toApiError() = Error(
    code = this.code.trString(),
    group = this.group.trString(),
    field = this.field.trString(),
    title = this.message.trString(),
    description = null,
)

private fun String.trString(): String? = takeIf { it.isNotBlank() }

private fun ResourcesContext.toApiResult(): ResponseResult? = when (this.state) {
    ResourcesState.NONE -> null
    ResourcesState.RUNNING -> ResponseResult.SUCCESS
    ResourcesState.FAILING -> ResponseResult.ERROR
    ResourcesState.FINISHING -> ResponseResult.SUCCESS
}

private fun ResourcesContext.toApiRequestId(): String? = this.requestId.takeIf { it != ResourcesRequestId.NONE }?.asString()
