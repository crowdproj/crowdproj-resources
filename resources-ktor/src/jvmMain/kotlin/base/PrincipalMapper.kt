package com.crowdproj.resources.app.base

import com.crowdproj.resources.app.base.KtorAuthConfig.Companion.F_NAME_CLAIM
import com.crowdproj.resources.app.base.KtorAuthConfig.Companion.GROUPS_CLAIM
import com.crowdproj.resources.app.base.KtorAuthConfig.Companion.ID_CLAIM
import com.crowdproj.resources.app.base.KtorAuthConfig.Companion.L_NAME_CLAIM
import com.crowdproj.resources.app.base.KtorAuthConfig.Companion.M_NAME_CLAIM
import com.crowdproj.resources.common.models.ResourcesUserId
import com.crowdproj.resources.common.permissions.ResourcesPrincipalModel
import com.crowdproj.resources.common.permissions.ResourcesUserGroups
import io.ktor.server.auth.jwt.*

fun JWTPrincipal?.toModel() = this?.run {
    ResourcesPrincipalModel(
        id = payload.getClaim(ID_CLAIM).asString()?.let { ResourcesUserId(it) } ?: ResourcesUserId.NONE,
        fname = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        mname = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lname = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
        groups = payload
            .getClaim(GROUPS_CLAIM)
            ?.asList(String::class.java)
            ?.mapNotNull {
                when (it) {
                    "USER" -> ResourcesUserGroups.USER
                    else -> null
                }
            }?.toSet() ?: emptySet()
    )
} ?: ResourcesPrincipalModel.NONE