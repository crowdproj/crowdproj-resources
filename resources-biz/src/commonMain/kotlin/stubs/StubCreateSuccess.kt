package com.crowdproj.resources.biz.stubs

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.models.ResourcesVisible
import com.crowdproj.resources.common.stubs.ResourcesStubs
import com.crowdproj.resources.stubs.CpwResourceStub
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.models.OtherResourcesId
import com.crowdproj.resources.common.models.ScheduleId

fun ICorAddExecDsl<ResourcesContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == ResourcesStubs.SUCCESS && state == ResourcesState.RUNNING }
    handle {
        state = ResourcesState.FINISHING
        val stub = CpwResourceStub.prepareResult {
            resourceRequest.resourcesId.takeIf { it != OtherResourcesId.NONE } ?.also { this.resourcesId = it }
            resourceRequest.scheduleId.takeIf { it != ScheduleId.NONE } ?.also { this.scheduleId = it }
            resourceRequest.visible.takeIf { it != ResourcesVisible.NONE } ?.also { this.visible = it }
        }
        resourceResponse = stub
    }
}