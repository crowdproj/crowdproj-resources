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
    else -> {}
}

fun ResourcesContext.fromApi(request: ResourceCreateRequest) {
    resolveOperation(request)
    fromApiResourceCreate(request.resource)
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ResourcesContext.fromApi(request: ResourceReadRequest) {
    resolveOperation(request)
    fromApiResourceRead(request.resource)
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ResourcesContext.fromApi(request: ResourceUpdateRequest) {
    resolveOperation(request)
    fromApiResourceUpdate(request.resource)
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ResourcesContext.fromApi(request: ResourceDeleteRequest) {
    resolveOperation(request)
    fromApiResourceDelete(request.resource)
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ResourcesContext.fromApi(request: ResourceSearchRequest) {
    resolveOperation(request)
    fromApiResourceSearch(request.resourceFilter)
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
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
    this.resourceRequest.lock = resource?.lock?.toResourcesLock() ?: ResourcesLock.NONE
}

private fun ResourcesContext.fromApiResourceSearch(filter: ResourceSearchFilter?) {
    this.resourceFilterRequest = ResourcesFilter(
        searchString = filter?.searchString ?: "",
    )
}

private fun String?.toResourceId() = this?.let { ResourcesId(it) } ?: ResourcesId.NONE
private fun String?.toResourcesLock() = this?.let { ResourcesLock(it) } ?: ResourcesLock.NONE
private fun String?.fromApiOtherResourcesId() = this?.let { OtherResourcesId(it) } ?: OtherResourcesId.NONE
private fun String?.fromApiScheduleId() = this?.let { ScheduleId(it) } ?: ScheduleId.NONE

private fun CpBaseDebug?.transportToWorkMode(): ResourcesWorkMode = when (this?.mode) {
    CpRequestDebugMode.PROD -> ResourcesWorkMode.PROD
    CpRequestDebugMode.TEST -> ResourcesWorkMode.TEST
    CpRequestDebugMode.STUB -> ResourcesWorkMode.STUB
    null -> ResourcesWorkMode.PROD
}

private fun CpBaseDebug?.transportToStubCase(): ResourcesStubs = when (this?.stub) {
    CpRequestDebugStubs.SUCCESS -> ResourcesStubs.SUCCESS
    CpRequestDebugStubs.NOT_FOUND -> ResourcesStubs.NOT_FOUND
    CpRequestDebugStubs.BAD_ID -> ResourcesStubs.BAD_ID
    null -> ResourcesStubs.NONE
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
