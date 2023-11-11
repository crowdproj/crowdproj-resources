package ru.otus.otuskotlin.marketplace.app.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.app.ResourcesAppSettings
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.ResourcesCorSettings


fun Application.initAppSettings(): ResourcesAppSettings {
    val corSettings = ResourcesCorSettings(
        loggerProvider = getLoggerProviderConf()
    )
    return ResourcesAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = ResourcesProcessor(corSettings),
    )
}