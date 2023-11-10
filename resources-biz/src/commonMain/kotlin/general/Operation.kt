package com.crowdproj.resources.biz.general

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesCommand
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain

fun ICorAddExecDsl<ResourcesContext>.operation(title: String, command: ResourcesCommand, block: ICorAddExecDsl<ResourcesContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == ResourcesState.RUNNING }
}