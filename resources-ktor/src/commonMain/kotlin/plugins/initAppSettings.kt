package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.app.base.KtorAuthConfig
import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.ResourcesCorSettings
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
        auth = initAppAuth()
    )
}

private fun Application.initAppAuth(): KtorAuthConfig = KtorAuthConfig(
    secret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "",
    issuer = environment.config.property("jwt.issuer").getString(),
    audience = environment.config.property("jwt.audience").getString(),
    realm = environment.config.property("jwt.realm").getString(),
    clientId = environment.config.property("jwt.clientId").getString(),
    certUrl = environment.config.propertyOrNull("jwt.certUrl")?.getString(),
)