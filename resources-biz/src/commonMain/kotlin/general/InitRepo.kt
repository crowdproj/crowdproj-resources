package com.crowdproj.resources.biz.general

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesWorkMode
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.helpers.errorAdministration
import com.crowdproj.resources.common.helpers.fail
import com.crowdproj.resources.common.repo.IResourceRepository

fun ICorAddExecDsl<ResourcesContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        resourceRepo = when (workMode) {
            ResourcesWorkMode.TEST -> settings.repoTest
            ResourcesWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != ResourcesWorkMode.STUB && resourceRepo == IResourceRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}