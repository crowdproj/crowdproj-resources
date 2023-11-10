package com.crowdproj.resources.app.plugins

import com.benasher44.uuid.uuid4
import com.crowdproj.resources.app.configs.ResourceAppSettings
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun Application.initRest(appConfig: ResourceAppSettings) {
    install(Routing)
    install(IgnoreTrailingSlash)
    install(AutoHeadResponse)
    install(CallId) {
        retrieveFromHeader("X-Request-ID")
        val rx = Regex("^[0-9a-zA-Z-]{3,100}\$")
        verify { rx.matches(it) }
        generate { "rq-${uuid4()}" }
    }
    install(ContentNegotiation) {
        json(appConfig.json)
    }
}