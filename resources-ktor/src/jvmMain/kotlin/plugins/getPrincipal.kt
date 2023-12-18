package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.app.base.toModel
import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.common.permissions.ResourcesPrincipalModel
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

actual fun ApplicationCall.getPrincipal(appSettings: ResourceAppSettings): ResourcesPrincipalModel =
    principal<JWTPrincipal>().toModel()