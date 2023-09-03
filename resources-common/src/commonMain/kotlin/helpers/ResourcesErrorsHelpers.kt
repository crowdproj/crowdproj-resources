package ru.otus.otuskotlin.marketplace.common.helpers

import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.ResourcesError

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