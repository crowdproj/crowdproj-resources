package com.crowdproj.resources.biz.stubs

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesError
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.helpers.fail

fun ICorAddExecDsl<ResourcesContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == ResourcesState.RUNNING }
    handle {
        fail(
            ResourcesError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}