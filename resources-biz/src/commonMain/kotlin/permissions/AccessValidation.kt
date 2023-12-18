package com.crowdproj.resources.biz.permissions

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.auth.checkPermitted
import com.crowdproj.resources.auth.resolveRelationsTo
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.helpers.fail
import com.crowdproj.resources.common.models.ResourcesError
import com.crowdproj.resources.common.models.ResourcesState

fun ICorAddExecDsl<ResourcesContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == ResourcesState.RUNNING }
    worker("Вычисление отношения свойства к принципалу") {
        resourceRepoRead.principalRelations = resourceRepoRead.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к свойству") {
        permitted = checkPermitted(command, resourceRepoRead.principalRelations, permissionsChain)
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(ResourcesError(message = "User is not allowed to perform this operation"))
        }
    }
}

fun ICorAddExecDsl<ResourcesContext>.accessValidationProps(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == ResourcesState.RUNNING }
    worker("Вычисление отношения свойств к принципалу") {
        resourcesRepoRead.forEach { it.principalRelations = it.resolveRelationsTo(principal) }
    }
    worker("Вычисление доступа к свойствам") {
        resourcesRepoRead.forEach {
            permitted = checkPermitted(command, it.principalRelations, permissionsChain)
            if (!permitted) return@forEach
        }
        if (resourcesRepoRead.isEmpty()) permitted = true
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(ResourcesError(message = "User is not allowed to perform this operation"))
        }
    }
}