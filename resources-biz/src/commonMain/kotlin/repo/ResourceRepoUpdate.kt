package com.crowdproj.resources.biz.repo

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.repo.DbResourceRequest
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<ResourcesContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == ResourcesState.RUNNING }
    handle {
        val request = DbResourceRequest(resourceRepoPrepare)
        val result = resourceRepo.updateResource(request)
        val resultResource = result.data
        if (result.isSuccess && resultResource != null) {
            resourceRepoDone = resultResource
        } else {
            state = ResourcesState.FAILING
            errors.addAll(result.errors)
            resourceRepoDone
        }
    }
}