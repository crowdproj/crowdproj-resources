package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.common.permissions.ResourcesPrincipalModel
import io.ktor.server.application.*

expect fun ApplicationCall.getPrincipal(appSettings: ResourceAppSettings): ResourcesPrincipalModel