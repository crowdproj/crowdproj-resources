package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.helpers.errorValidation
import com.crowdproj.resources.common.helpers.fail
import com.crowdproj.resources.common.models.OtherResourcesId

fun ICorAddExecDsl<ResourcesContext>.validateOtherIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в OtherResourcesId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { resourceValidating.resourcesId != OtherResourcesId.NONE && ! resourceValidating.resourcesId.asString().matches(regExp) }
    handle {
        val encodedId = resourceValidating.resourcesId.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "resourcesId",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}