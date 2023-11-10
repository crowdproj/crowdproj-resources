package com.crowdproj.resources.biz.stubs

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.stubs.ResourcesStubs
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.stubs.CpwResourceStub

fun ICorAddExecDsl<ResourcesContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == ResourcesStubs.SUCCESS && state == ResourcesState.RUNNING }
    handle {
        state = ResourcesState.FINISHING
        val stub = CpwResourceStub.prepareResult {
            resourceRequest.id.takeIf { it != ResourcesId.NONE }?.also { this.id = it }
            resourceRequest.resourcesId.takeIf { it != OtherResourcesId.NONE }?.also { this.resourcesId = it }
            resourceRequest.scheduleId.takeIf { it != ScheduleId.NONE }?.also { this.scheduleId = it }
            resourceRequest.visible.takeIf { it != ResourcesVisible.NONE } ?.also { this.visible = it }
        }
        resourceResponse = stub
    }
}