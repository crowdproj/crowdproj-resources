package com.crowdproj.resources.app.plugins

import io.ktor.server.application.*
import com.crowdproj.resources.logging.common.MpLoggerProvider

expect fun Application.getLoggerProviderConf(): MpLoggerProvider
