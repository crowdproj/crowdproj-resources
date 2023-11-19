package com.crowdproj.resources.biz.stubs

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesError
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.stubs.ResourcesStubs
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<ResourcesContext>.stubValidationScheduleId(title: String) = worker {
    this.title = title
    on { stubCase == ResourcesStubs.BAD_SCHEDULE_ID && state == ResourcesState.RUNNING }
    handle {
        state = ResourcesState.FAILING
        this.errors.add(
            ResourcesError(
                group = "validation",
                code = "validation-schedule-id",
                field = "scheduleId",
                message = "Wrong schedule-id field"
            )
        )
    }
}