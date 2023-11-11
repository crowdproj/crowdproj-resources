@file:Suppress("DuplicatedCode")
package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.app.ResourcesAppSettings

fun Route.v2Ad(appSettings: ResourcesAppSettings) {
    route("resource") {
        post("create") {
            call.createRes(appSettings)
        }
        post("read") {
            call.readRes(appSettings)
        }
        post("update") {
            call.updateRes(appSettings)
        }
        post("delete") {
            call.deleteRes(appSettings)
        }
        post("search") {
            call.searchRes(appSettings)
        }
    }
}