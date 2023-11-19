package com.crowdproj.resources.app.controller

import com.crowdproj.resources.app.configs.ResourceAppSettings
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.v1ProductProperty(appSettings: ResourceAppSettings) {
    val loggerResource = appSettings.corSettings.loggerProvider.logger(Route::v1ProductProperty)

    route("resource") {
        post("create") {
            call.createResource(appSettings, loggerResource)
        }
        post("update") {
            call.updateResource(appSettings, loggerResource)
        }
        post("delete") {
            call.deleteResource(appSettings, loggerResource)
        }
        post("search") {
            call.searchResource(appSettings, loggerResource)
        }
        post("read") {
            call.readResource(appSettings, loggerResource)
        }
    }
}