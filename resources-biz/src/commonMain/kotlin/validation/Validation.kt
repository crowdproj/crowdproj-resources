package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain

fun ICorAddExecDsl<ResourcesContext>.validation(block: ICorAddExecDsl<ResourcesContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == ResourcesState.RUNNING }
}