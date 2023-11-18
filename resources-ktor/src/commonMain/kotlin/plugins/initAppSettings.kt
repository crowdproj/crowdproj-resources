package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.ResourcesCorSettings
import com.crowdproj.resources.logging.common.MpLoggerProvider
import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.ResourceRepoStub

fun Application.initAppSettings(): ResourceAppSettings {
    return ResourceAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList()?.filter { it.isNotBlank() }?: emptyList(),
        corSettings = ResourcesCorSettings(
            loggerProvider = getLoggerProviderConf(),
            repoTest = getDatabaseConf(ResDbType.TEST),
            repoProd = getDatabaseConf(ResDbType.PROD),
            repoStub = ResourceRepoStub(),
        ),
        processor = ResourcesProcessor(),
    )
}

expect fun Application.getLoggerProviderConf(): MpLoggerProvider