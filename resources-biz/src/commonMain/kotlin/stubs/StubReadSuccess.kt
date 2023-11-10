package com.crowdproj.resources.biz.stubs

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.stubs.ResourcesStubs
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.models.ResourcesId
import com.crowdproj.resources.stubs.CpwResourceStub

fun ICorAddExecDsl<ResourcesContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == ResourcesStubs.SUCCESS && state == ResourcesState.RUNNING }
    handle {
        state = ResourcesState.FINISHING
        val stub = CpwResourceStub.prepareResult {
            resourceRequest.id.takeIf { it != ResourcesId.NONE } ?.also { this.id = it }
        }
        resourceResponse = stub
    }
}
