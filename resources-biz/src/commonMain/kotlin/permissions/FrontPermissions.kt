package com.crowdproj.resources.biz.permissions

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.auth.resolveFrontPermissions
import com.crowdproj.resources.auth.resolveRelationsTo
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesState

fun ICorAddExecDsl<ResourcesContext>.frontPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { state == ResourcesState.RUNNING }

    handle {
        resourceRepoDone.permissionsClient.addAll(
            resolveFrontPermissions(
                permissionsChain,
                // Повторно вычисляем отношения, поскольку они могли измениться при выполении операции
                resourceRepoDone.resolveRelationsTo(principal)
            )
        )

        for (prop in resourcesRepoDone) {
            prop.permissionsClient.addAll(
                resolveFrontPermissions(
                    permissionsChain,
                    prop.resolveRelationsTo(principal)
                )
            )
        }
    }
}