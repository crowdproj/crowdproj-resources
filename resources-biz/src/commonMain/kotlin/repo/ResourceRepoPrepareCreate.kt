package com.crowdproj.resources.biz.repo

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<ResourcesContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == ResourcesState.RUNNING }
    handle {
        resourceRepoRead = resourceValidated.deepCopy()
        resourceRepoPrepare = resourceRepoRead

    }
}