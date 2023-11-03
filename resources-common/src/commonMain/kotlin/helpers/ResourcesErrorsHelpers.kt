package ru.otus.otuskotlin.marketplace.common.helpers

import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.ResourcesError
import ru.otus.otuskotlin.marketplace.common.models.ResourcesState

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

fun ResourcesContext.fail(error: ResourcesError) {
    addError(error)
    state = ResourcesState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: ResourcesError.Level = ResourcesError.Level.ERROR,
) = ResourcesError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)