package com.crowdproj.resources.app

import com.crowdproj.resources.app.plugins.initAppSettings
import io.ktor.server.application.*
import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.app.controller.v1Resources
import com.crowdproj.resources.app.controller.wsHandlerV1
import com.crowdproj.resources.app.plugins.initPlugins
import io.ktor.server.routing.*
import io.ktor.server.websocket.*

fun Application.module(appSettings: ResourceAppSettings = initAppSettings()) {
    initPlugins(appSettings)

    routing {
        route("v1") {
            v1Resources(appSettings)

            webSocket("ws") {
                wsHandlerV1(appSettings)
            }
        }
    }
}