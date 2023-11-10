package ru.otus.otuskotlin.marketplace.app

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.app.plugins.initAppSettings
import ru.otus.otuskotlin.marketplace.app.plugins.swagger
import ru.otus.otuskotlin.marketplace.app.v1.v1Resources
import ru.otus.otuskotlin.marketplace.app.v1.wsHandlerV1
import ru.otus.otuskotlin.marketplace.logging.jvm.MpLogWrapperLogback

private val clazz = Application::moduleJvm::class.qualifiedName ?: "Application"
@Suppress("unused")
// Referenced in application.conf_
fun Application.moduleJvm(appSettings: ResourcesAppSettings = initAppSettings()) {
    module(appSettings)

    install(CallLogging) {
        level = Level.INFO
        val lgr = appSettings
            .corSettings
            .loggerProvider
            .logger(clazz) as? MpLogWrapperLogback
        lgr?.logger?.also { logger = it }
    }

    routing {
        route("v1") {
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiV1Mapper.serializationConfig)
                    setConfig(apiV1Mapper.deserializationConfig)
                }
            }

            v1Resources(appSettings)
        }

        webSocket("/ws/v1") {
            wsHandlerV1(appSettings)
        }
        swagger(appSettings)
        static("static") {
            resources("static")
        }
    }
}
