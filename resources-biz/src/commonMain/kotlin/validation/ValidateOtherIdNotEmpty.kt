package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.helpers.errorValidation
import com.crowdproj.resources.common.helpers.fail

fun ICorAddExecDsl<ResourcesContext>.validateOtherIdNotEmpty(title: String) = worker {
    this.title = title
    on { resourceValidating.resourcesId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "resourcesId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}