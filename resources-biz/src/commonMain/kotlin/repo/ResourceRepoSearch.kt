package com.crowdproj.resources.biz.repo

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.repo.DbResourceFilterRequest
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<ResourcesContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == ResourcesState.RUNNING }
    handle {
        val request = DbResourceFilterRequest(
            titleFilter = resourceFilterValidated.searchString,
            ownerId = resourceFilterValidated.ownerId,
        )
        val result = resourceRepo.searchResource(request)
        val resultResources = result.data
        if (result.isSuccess && resultResources != null) {
            resourcesRepoDone = resultResources.toMutableList()
        } else {
            state = ResourcesState.FAILING
            errors.addAll(result.errors)
        }
    }
}