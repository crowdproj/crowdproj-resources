package com.crowdproj.resources.api.logs.mapper

import com.crowdproj.resources.api.logs.models.*
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.*
import kotlinx.datetime.Clock

fun ResourcesContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "crowdproj-resources",
    resource = toMkplLog(),
    errors = errors.map { it.toLog() },
)

fun ResourcesContext.toMkplLog(): ResourceLogModel? {
    val resBlank = Resources()
    return ResourceLogModel(
        requestId = requestId.takeIf { it != ResourcesRequestId.NONE }?.asString(),
        operation = command.takeIf { it != ResourcesCommand.NONE }?.name?.let { ResourceLogModel.Operation.valueOf(it) },
        requestResource = resourceRequest.takeIf { it != resBlank }?.toLog(),
        responseResource = resourceResponse.takeIf { it != resBlank }?.toLog(),
        responseResources = resourcesResponse.takeIf { it.isNotEmpty() }
            ?.filter { it != resBlank }
            ?.map { it.toLog() },
        requestFilter = resourceFilterRequest.takeIf { it != ResourcesFilter() }?.toLog(),
    ).takeIf { it != ResourceLogModel() }
}

fun ResourcesError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun Resources.toLog() = ResourceLog(
    id = id.takeIf { it != ResourcesId.NONE }?.asString(),
    resourceId = resourcesId.takeIf { it != OtherResourcesId.NONE }?.asString(),
    scheduleId = scheduleId.takeIf { it != ScheduleId.NONE }?.asString(),
    deleted = deleted
)

private fun ResourcesFilter.toLog() = ResourceFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
)
