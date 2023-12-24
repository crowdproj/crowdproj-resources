package com.crowdproj.resources.biz.permissions

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.auth.resolveChainPermissions
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState


fun ICorAddExecDsl<ResourcesContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей"

    on { state == ResourcesState.RUNNING }

    handle {
        permissionsChain.addAll(resolveChainPermissions(principal.groups))
        println("PRINCIPAL: $principal")
        println("PERMISSIONS: $permissionsChain")
    }
}