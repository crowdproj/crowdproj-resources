package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.helpers.errorValidation
import com.crowdproj.resources.common.helpers.fail

fun ICorAddExecDsl<ResourcesContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { resourceValidating.id.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}