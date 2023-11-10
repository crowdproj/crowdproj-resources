package com.crowdproj.resources.app.plugins

//import com.crowdproj.resources.repo.inmemory.CwpAdRepoInMemory
import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.common.config.ResourcesCorSettings
import com.crowdproj.resources.common.repo.IResourceRepository
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
            repoProd = IResourceRepository.NONE, //CwpAdRepoInMemory(),
            repoTest = IResourceRepository.NONE, //CwpAdRepoInMemory(),
            repoStub = ResourceRepoStub(),
        )
    )
}