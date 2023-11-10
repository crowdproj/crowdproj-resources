package com.crowdproj.resources.biz.general

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<ResourcesContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == ResourcesState.NONE }
    handle { state = ResourcesState.RUNNING }
}