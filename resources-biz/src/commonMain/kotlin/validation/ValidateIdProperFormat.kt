package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesId
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.helpers.errorValidation
import com.crowdproj.resources.common.helpers.fail

fun ICorAddExecDsl<ResourcesContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в ResourcesId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { resourceValidating.id != ResourcesId.NONE && ! resourceValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = resourceValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}