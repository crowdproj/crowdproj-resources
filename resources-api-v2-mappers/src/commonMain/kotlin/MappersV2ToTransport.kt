package ru.otus.otuskotlin.marketplace.mappers.v2

import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.mappers.v2.exceptions.UnknownMkplCommand

fun ResourcesContext.toTransportAd(): IResponse = when (val cmd = command) {
    ResourcesCommand.CREATE -> toTransportCreate()
    ResourcesCommand.READ -> toTransportRead()
    ResourcesCommand.UPDATE -> toTransportUpdate()
    ResourcesCommand.DELETE -> toTransportDelete()
    ResourcesCommand.SEARCH -> toTransportSearch()
    ResourcesCommand.INIT -> toTransportInit()
    ResourcesCommand.FINISH -> throw UnknownMkplCommand(cmd)
    ResourcesCommand.NONE -> throw UnknownMkplCommand(cmd)

}

fun ResourcesContext.toTransportCreate() = ResourceCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    resource = resourceResponse.toTransportResource()
)

fun ResourcesContext.toTransportRead() = ResourceReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    resource = resourceResponse.toTransportResource()
)

fun ResourcesContext.toTransportUpdate() = ResourceUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    resource = resourceResponse.toTransportResource()
)

fun ResourcesContext.toTransportDelete() = ResourceDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    resource = resourceResponse.toTransportResource()
)

fun ResourcesContext.toTransportSearch() = ResourceSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    resources = resourcesResponse.toTransportResource()
)

fun ResourcesContext.toTransportInit() = ResourceInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = state.toResult(),
    errors = errors.toTransportErrors(),
)

fun List<Resources>.toTransportResource(): List<ResourceResponseObject>? = this
    .map { it.toTransportResource() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Resources.toTransportResource(): ResourceResponseObject = ResourceResponseObject(
    id = id.takeIf { it != ResourcesId.NONE }?.asString(),
    resourceId = resourcesId.takeIf { it != OtherResourcesId.NONE }?.asString(),
    scheduleId = scheduleId.takeIf { it != ScheduleId.NONE }?.asString(),
    ownerId = ownerId.takeIf { it != ResourcesUserId.NONE }?.asString(),
    visible = visible.toTransportAd(),
    permissions = permissionsClient.toTransportAd(),
)

private fun Set<ResourcesPermissionClient>.toTransportAd(): Set<ResourcePermissions>? = this
    .map { it.toTransportAd() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun ResourcesPermissionClient.toTransportAd() = when (this) {
    ResourcesPermissionClient.READ -> ResourcePermissions.READ
    ResourcesPermissionClient.UPDATE -> ResourcePermissions.UPDATE
    ResourcesPermissionClient.MAKE_VISIBLE_OWNER -> ResourcePermissions.MAKE_VISIBLE_OWN
    ResourcesPermissionClient.MAKE_VISIBLE_GROUP -> ResourcePermissions.MAKE_VISIBLE_GROUP
    ResourcesPermissionClient.MAKE_VISIBLE_PUBLIC -> ResourcePermissions.MAKE_VISIBLE_PUBLIC
    ResourcesPermissionClient.DELETE -> ResourcePermissions.DELETE
}

private fun ResourcesVisible.toTransportAd(): ResourceVisibility? = when (this) {
    ResourcesVisible.VISIBLE_PUBLIC -> ResourceVisibility.PUBLIC
    ResourcesVisible.VISIBLE_TO_GROUP -> ResourceVisibility.REGISTERED_ONLY
    ResourcesVisible.VISIBLE_TO_OWNER -> ResourceVisibility.OWNER_ONLY
    ResourcesVisible.NONE -> null
}

private fun List<ResourcesError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ResourcesError.toTransportAd() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun ResourcesState.toResult(): ResponseResult? = when (this) {
    ResourcesState.RUNNING -> ResponseResult.SUCCESS
    ResourcesState.FAILING -> ResponseResult.ERROR
    ResourcesState.FINISHING -> ResponseResult.SUCCESS
    ResourcesState.NONE -> null
}