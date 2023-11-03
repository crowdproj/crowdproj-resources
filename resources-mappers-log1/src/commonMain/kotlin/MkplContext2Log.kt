package ru.otus.otuskotlin.marketplace.api.logs.mapper

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.api.logs.models.*
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.*

fun ResourcesContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "resources",
    resource = toMkplLog(),
    errors = errors.map { it.toLog() },
)

fun ResourcesContext.toMkplLog():ResourceLogModel? {
    val resNone = Resources()
    return ResourceLogModel(
        requestId = requestId.takeIf { it != ResourcesRequestId.NONE }?.asString(),
        operation = command.toLogModel(),
        requestAd = resourceRequest.takeIf { it != resNone }?.toLog(),
        responseAd = resourceResponse.takeIf { it != resNone }?.toLog(),
        responseAds = resourcesResponse.takeIf { it.isNotEmpty() }?.filter { it != resNone }?.map { it.toLog() },
        requestFilter = resourceFilterRequest.takeIf { it != ResourcesFilter() }?.toLog(),
    ).takeIf { it != ResourceLogModel() }
}

private fun ResourcesCommand.toLogModel(): ResourceLogModel.Operation? = when(this) {
    ResourcesCommand.CREATE -> ResourceLogModel.Operation.CREATE
    ResourcesCommand.READ -> ResourceLogModel.Operation.READ
    ResourcesCommand.UPDATE -> ResourceLogModel.Operation.UPDATE
    ResourcesCommand.DELETE -> ResourceLogModel.Operation.DELETE
    ResourcesCommand.SEARCH -> ResourceLogModel.Operation.SEARCH
    ResourcesCommand.INIT -> ResourceLogModel.Operation.INIT
    ResourcesCommand.FINISH -> ResourceLogModel.Operation.FINISH
    ResourcesCommand.NONE -> null
}

private fun ResourcesFilter.toLog() = ResourceFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != ResourcesUserId.NONE }?.asString(),
)

fun ResourcesError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun Resources.toLog() = ResourceLog(
    id = id.takeIf { it != ResourcesId.NONE }?.asString(),
    resourcesId = resourcesId.takeIf { it != OtherResourcesId.NONE }?.asString(),
    scheduleId = scheduleId.takeIf { it != ScheduleId.NONE }?.asString(),
    visible = visible.takeIf { it != ResourcesVisible.NONE }?.name,
    ownerId = ownerId.takeIf { it != ResourcesUserId.NONE }?.asString(),
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)
