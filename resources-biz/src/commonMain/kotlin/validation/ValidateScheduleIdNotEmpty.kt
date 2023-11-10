package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.helpers.errorValidation
import com.crowdproj.resources.common.helpers.fail

fun ICorAddExecDsl<ResourcesContext>.validateScheduleIdNotEmpty(title: String) = worker {
    this.title = title
    on { resourceValidating.scheduleId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "scheduleId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}