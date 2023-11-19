package com.crowdproj.resources.biz.repo

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.repo.DbResourceIdRequest
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<ResourcesContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == ResourcesState.RUNNING }
    handle {
        val request = DbResourceIdRequest(resourceRepoPrepare)
        val result = resourceRepo.deleteResource(request)
        if (!result.isSuccess) {
            state = ResourcesState.FAILING
            errors.addAll(result.errors)
        }
        resourceRepoDone = resourceRepoRead
    }
}