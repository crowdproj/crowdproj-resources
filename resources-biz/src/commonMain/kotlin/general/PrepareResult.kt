package com.crowdproj.resources.biz.general

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.models.ResourcesWorkMode
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<ResourcesContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != ResourcesWorkMode.STUB }
    handle {
        resourceResponse = resourceRepoDone
        resourcesResponse = resourcesRepoDone
        state = when (val st = state) {
            ResourcesState.RUNNING -> ResourcesState.FINISHING
            else -> st
        }
    }
}