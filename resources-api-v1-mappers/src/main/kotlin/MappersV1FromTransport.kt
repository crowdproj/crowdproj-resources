package ru.otus.otuskotlin.marketplace.mappers.v1

import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.stubs.ResourcesStubs
import ru.otus.otuskotlin.marketplace.mappers.v1.exceptions.UnknownRequestClass

fun ResourcesContext.fromTransport(request: IRequest) = when (request) {
    is ResourceCreateRequest -> fromTransport(request)
    is ResourceReadRequest -> fromTransport(request)
    is ResourceUpdateRequest -> fromTransport(request)
    is ResourceDeleteRequest -> fromTransport(request)
    is ResourceSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toAdId() = this?.let { ResourcesId(it) } ?: ResourcesId.NONE
private fun String?.toAdWithId() = Resources(id = this.toAdId())
private fun IRequest?.requestId() = this?.requestId?.let { ResourcesRequestId(it) } ?: ResourcesRequestId.NONE

private fun ResourceDebug?.transportToWorkMode(): ResourcesWorkMode = when (this?.mode) {
    ResourceRequestDebugMode.PROD -> ResourcesWorkMode.PROD
    ResourceRequestDebugMode.TEST -> ResourcesWorkMode.TEST
    ResourceRequestDebugMode.STUB -> ResourcesWorkMode.STUB
    null -> ResourcesWorkMode.PROD
}

private fun ResourceDebug?.transportToStubCase(): ResourcesStubs = when (this?.stub) {
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

fun ResourcesContext.fromTransport(request: ResourceCreateRequest) {
    command = ResourcesCommand.CREATE
    requestId = request.requestId()
    resourceRequest = request.resource?.toInternal() ?: Resources()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ResourcesContext.fromTransport(request: ResourceReadRequest) {
    command = ResourcesCommand.READ
    requestId = request.requestId()
    resourceRequest = request.resource?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ResourcesContext.fromTransport(request: ResourceUpdateRequest) {
    command = ResourcesCommand.UPDATE
    requestId = request.requestId()
    resourceRequest = request.resource?.toInternal() ?: Resources()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ResourcesContext.fromTransport(request: ResourceDeleteRequest) {
    command = ResourcesCommand.DELETE
    requestId = request.requestId()
    resourceRequest = request.resource?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ResourcesContext.fromTransport(request: ResourceSearchRequest) {
    command = ResourcesCommand.SEARCH
    requestId = request.requestId()
    resourceFilterRequest = request.resourceFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ResourceSearchFilter?.toInternal(): ResourcesFilter = ResourcesFilter(
    searchString = this?.searchString ?: ""
)
private fun String?.toOtherId() = this?.let { OtherResourcesId(it) } ?: OtherResourcesId.NONE
private fun String?.toScheduleId() = this?.let { ScheduleId(it) } ?: ScheduleId.NONE
private fun ResourceCreateObject.toInternal(): Resources = Resources(
    resourcesId = this.resourceId.toOtherId(),
    scheduleId = this.scheduleId.toScheduleId(),
    visible = this.visible.fromTransport(),
)

private fun ResourceUpdateObject.toInternal(): Resources = Resources(
    id = this.id.toAdId(),
    resourcesId = this.resourceId.toOtherId(),
    scheduleId = this.scheduleId.toScheduleId(),
    visible = this.visible.fromTransport(),
)

private fun ResourceVisibility?.fromTransport(): ResourcesVisible = when (this) {
    ResourceVisibility.PUBLIC -> ResourcesVisible.VISIBLE_PUBLIC
    ResourceVisibility.OWNER_ONLY -> ResourcesVisible.VISIBLE_TO_OWNER
    ResourceVisibility.REGISTERED_ONLY -> ResourcesVisible.VISIBLE_TO_GROUP
    null -> ResourcesVisible.NONE
}