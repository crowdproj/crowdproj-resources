package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.logging.common.MpLoggerProvider
import com.crowdproj.resources.logging.logback.resourceLoggerLogback
import io.ktor.server.application.*

actual fun Application.getLoggerProviderConf(): MpLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> MpLoggerProvider { resourceLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values are logback")
    }