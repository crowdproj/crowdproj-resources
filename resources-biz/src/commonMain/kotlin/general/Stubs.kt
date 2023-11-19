package com.crowdproj.resources.biz.general

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.models.ResourcesWorkMode
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain

fun ICorAddExecDsl<ResourcesContext>.stubs(title: String, block: ICorAddExecDsl<ResourcesContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == ResourcesWorkMode.STUB && state == ResourcesState.RUNNING }
}