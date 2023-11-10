package com.crowdproj.resources.biz.repo

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.repo.DbResourceIdRequest
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<ResourcesContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == ResourcesState.RUNNING }
    handle {
        val request = DbResourceIdRequest(resourceValidated)
        val result = resourceRepo.readResource(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            resourceRepoRead = resultAd
        } else {
            state = ResourcesState.FAILING
            errors.addAll(result.errors)
        }
    }
}