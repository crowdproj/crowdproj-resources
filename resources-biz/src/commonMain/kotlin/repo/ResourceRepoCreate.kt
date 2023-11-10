package com.crowdproj.resources.biz.repo

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.repo.DbResourceRequest
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<ResourcesContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление объявления в БД"
    on { state == ResourcesState.RUNNING }
    handle {
        val request = DbResourceRequest(resourceRepoPrepare)
        val result = resourceRepo.createResource(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            resourceRepoDone = resultAd
        } else {
            state = ResourcesState.FAILING
            errors.addAll(result.errors)
        }
    }
}