package com.crowdproj.resources.common.helpers

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesError

fun Throwable.asMkplError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = ResourcesError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun ResourcesContext.addError(vararg error: ResourcesError) = errors.addAll(error)