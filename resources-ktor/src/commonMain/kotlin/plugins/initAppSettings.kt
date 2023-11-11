package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.repo.inmemory.ResourcesRepoInMemory
import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.common.config.ResourcesCorSettings
import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.ResourceRepoStub

fun Application.initAppSettings(): ResourceAppSettings {
    return ResourceAppSettings(
        appUrls = environment.config
            .propertyOrNull("ktor.urls")
            ?.getList()
            ?.filter { it.isNotBlank() }
            ?: emptyList(),
        corSettings = ResourcesCorSettings(
            repoProd = ResourcesRepoInMemory(),
            repoTest = ResourcesRepoInMemory(),
            repoStub = ResourceRepoStub(),
        )
    )
}