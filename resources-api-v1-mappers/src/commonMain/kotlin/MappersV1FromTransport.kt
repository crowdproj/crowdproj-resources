package com.crowdproj.resources.mappers.v1

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.api.v1.models.*
import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.common.stubs.ResourcesStubs

fun ResourcesContext.fromApi(request: IRequestResource) = when (request) {
    is ResourceCreateRequest -> fromApi(request)
    is ResourceReadRequest -> fromApi(request)
    is ResourceUpdateRequest -> fromApi(request)
    is ResourceDeleteRequest -> fromApi(request)
    is ResourceSearchRequest -> fromApi(request)
}

fun ResourcesContext.fromApi(request: ResourceCreateRequest) {
    resolveOperation(request)
    fromApiResourceCreate(request.resource)
    fromApiDebug(request)
}

fun ResourcesContext.fromApi(request: ResourceReadRequest) {
    resolveOperation(request)
    fromApiResourceRead(request.resource)
    fromApiDebug(request)
}

fun ResourcesContext.fromApi(request: ResourceUpdateRequest) {
    resolveOperation(request)
    fromApiResourceUpdate(request.resource)
    fromApiDebug(request)
}

fun ResourcesContext.fromApi(request: ResourceDeleteRequest) {
    resolveOperation(request)
    fromApiResourceDelete(request.resource)
    fromApiDebug(request)
}

fun ResourcesContext.fromApi(request: ResourceSearchRequest) {
    resolveOperation(request)
    fromApiResourceSearch(request.resourceFilter)
    fromApiDebug(request)
}


private fun ResourcesContext.fromApiResourceCreate(resource: ResourceCreateObject?) {
    this.resourceRequest = resource?.let {
        Resources(
            resourcesId = it.resourceId.fromApiOtherResourcesId(),
            scheduleId = it.scheduleId.fromApiScheduleId(),
            visible = it.visible.fromApiVisible(),
        )
    } ?: Resources()
}

private fun ResourcesContext.fromApiResourceRead(resource: ResourceReadObject?) {
    this.resourceRequest.id = resource?.id?.toResourceId() ?: ResourcesId.NONE
}

private fun ResourcesContext.fromApiResourceUpdate(resource: ResourceUpdateObject?) {
    this.resourceRequest = resource?.let {
        Resources(
            id = it.id.toResourceId(),
            resourcesId = it.resourceId.fromApiOtherResourcesId(),
            scheduleId = it.scheduleId.fromApiScheduleId(),
            visible = it.visible.fromApiVisible(),
        )
    } ?: Resources()
}

private fun ResourcesContext.fromApiResourceDelete(resource: ResourceDeleteObject?) {
    this.resourceRequest.id = resource?.id?.toResourceId() ?: ResourcesId.NONE
}

private fun ResourcesContext.fromApiResourceSearch(filter: ResourceSearchFilter?) {
    this.resourceFilterRequest = ResourcesFilter(
        searchString = filter?.searchString ?: "",
    )
}

private fun String?.toResourceId() = this?.let { ResourcesId(it) } ?: ResourcesId.NONE
private fun String?.fromApiOtherResourcesId() = this?.let { OtherResourcesId(it) } ?: OtherResourcesId.NONE
private fun String?.fromApiScheduleId() = this?.let { ScheduleId(it) } ?: ScheduleId.NONE

private fun ResourceRequestDebugMode?.fromApiWorkMode(): ResourcesWorkMode = when (this) {
    ResourceRequestDebugMode.PROD -> ResourcesWorkMode.PROD
    ResourceRequestDebugMode.TEST -> ResourcesWorkMode.TEST
    ResourceRequestDebugMode.STUB -> ResourcesWorkMode.STUB
    null -> ResourcesWorkMode.NONE
}

private fun ResourceRequestDebugStubs?.fromApiStubCase(): ResourcesStubs = when (this) {
    ResourceRequestDebugStubs.SUCCESS -> ResourcesStubs.SUCCESS
    ResourceRequestDebugStubs.NOT_FOUND -> ResourcesStubs.NOT_FOUND
    ResourceRequestDebugStubs.BAD_ID -> ResourcesStubs.BAD_ID
    ResourceRequestDebugStubs.BAD_TITLE -> ResourcesStubs.BAD_TITLE
    ResourceRequestDebugStubs.BAD_DESCRIPTION -> ResourcesStubs.BAD_DESCRIPTION
    ResourceRequestDebugStubs.BAD_VISIBILITY -> ResourcesStubs.BAD_VISIBILITY
    ResourceRequestDebugStubs.CANNOT_DELETE -> ResourcesStubs.CANNOT_DELETE
    ResourceRequestDebugStubs.BAD_SEARCH_STRING -> ResourcesStubs.BAD_SEARCH_STRING
    null -> ResourcesStubs.NONE
}

private fun ResourcesContext.fromApiDebug(request: IRequestResource?) {
    this.workMode = request?.debug?.mode?.fromApiWorkMode() ?: ResourcesWorkMode.NONE
    this.stubCase = request?.debug?.stub?.fromApiStubCase() ?: ResourcesStubs.NONE
}

private fun ResourceVisibility?.fromApiVisible(): ResourcesVisible = when (this) {
    ResourceVisibility.OWNER_ONLY -> ResourcesVisible.VISIBLE_TO_OWNER
    ResourceVisibility.REGISTERED_ONLY -> ResourcesVisible.VISIBLE_TO_GROUP
    ResourceVisibility.PUBLIC -> ResourcesVisible.VISIBLE_PUBLIC
    null -> ResourcesVisible.NONE
}

private fun ResourcesContext.resolveOperation(request: IRequestResource) {
    this.command = when (request) {
        is ResourceCreateRequest -> ResourcesCommand.CREATE
        is ResourceReadRequest -> ResourcesCommand.READ
        is ResourceUpdateRequest -> ResourcesCommand.UPDATE
        is ResourceDeleteRequest -> ResourcesCommand.DELETE
        is ResourceSearchRequest -> ResourcesCommand.SEARCH

    }
}
