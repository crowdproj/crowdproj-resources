package com.crowdproj.resources.common.helpers

import com.crowdproj.resources.common.models.ResourcesError
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.exceptions.RepoConcurrencyException
import com.crowdproj.resources.common.models.ResourcesLock
import com.crowdproj.resources.common.models.ResourcesState

fun Throwable.asResourcesError(
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

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    level: ResourcesError.Level = ResourcesError.Level.ERROR,
) = ResourcesError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)

fun errorRepoConcurrency(
    expectedLock: ResourcesLock,
    actualLock: ResourcesLock?,
    exception: Exception? = null,
) = ResourcesError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

