package com.crowdproj.resources.app

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.config.yaml.*
import io.ktor.server.engine.*

//actual fun main(args: Array<String>) = io.ktor.server.cio.EngineMain.main(args)

fun main() {
    embeddedServer(CIO, environment = applicationEngineEnvironment {
        module {
            module()
        }
        val appConfig = YamlConfig("src/commonMain/resources/application.yaml")
        if (appConfig != null) {
            config = appConfig
        }
        connector {
            port = appConfig?.port ?: 8080
            host = "0.0.0.0"
        }
    })
        .start(wait = true)
}