package com.crowdproj.resources.app

import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.app.plugins.configureRouting
import com.crowdproj.resources.app.plugins.initAppSettings
import io.ktor.server.application.*

//fun main(args: Array<String>) = io.ktor.server.cio.EngineMain.main(args)
expect fun main(args: Array<String>)

@Suppress("unused")
fun Application.module(appSettings: ResourceAppSettings = initAppSettings()) {
    println("CONFIGS ${environment.config.toMap().size}")
    environment.config.toMap().forEach {
        println("${it.key} -> ${it.value}")
    }
    configureRouting(appSettings)
}