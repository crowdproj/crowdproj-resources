package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.helpers.errorValidation
import com.crowdproj.resources.common.helpers.fail
import com.crowdproj.resources.common.models.ScheduleId

fun ICorAddExecDsl<ResourcesContext>.validateScheduleIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в ScheduleId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { resourceValidating.scheduleId != ScheduleId.NONE && ! resourceValidating.scheduleId.asString().matches(regExp) }
    handle {
        val encodedId = resourceValidating.scheduleId.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "scheduleId",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}