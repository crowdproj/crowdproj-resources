package com.crowdproj.resources.app

import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.app.controller.v1ProductProperty
import com.crowdproj.resources.app.controller.wsHandlerV1
import com.crowdproj.resources.app.plugins.initAppSettings
import com.crowdproj.resources.app.plugins.initPlugins
import com.crowdproj.resources.logging.logback.CwpLogWrapperLogback
import com.crowdproj.resources.app.plugins.swagger
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.cio.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level

private val clazz = Application::moduleJvm::class.qualifiedName ?: "Application"

fun main(args: Array<String>) = EngineMain.main(args)

@Suppress("unused") // Referenced in application.yaml
fun Application.moduleJvm(
    appSettings: ResourceAppSettings = initAppSettings(),
) {
    initPlugins(appSettings)

    install(CallLogging) {
        level = Level.INFO
        val lgr = appSettings
            .corSettings
            .loggerProvider
            .logger(clazz) as? CwpLogWrapperLogback
        lgr?.logger?.also { logger = it }
    }
    install(DefaultHeaders)

    routing {
        route("v1") {

            v1ProductProperty(appSettings)

            webSocket("ws") {
                wsHandlerV1(appSettings)
            }
        }
        swagger(appSettings)
        static("static") {
            resources("static")
        }
    }
}