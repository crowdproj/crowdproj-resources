package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<ResourcesContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == ResourcesState.RUNNING }
    handle {
        resourceValidated = resourceValidating
    }
}

fun ICorAddExecDsl<ResourcesContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == ResourcesState.RUNNING }
    handle {
        resourceFilterValidated = resourceFilterValidating
    }
}