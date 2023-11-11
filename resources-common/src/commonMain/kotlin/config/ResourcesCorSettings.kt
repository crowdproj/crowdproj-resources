package com.crowdproj.resources.common.config

import com.crowdproj.resources.common.repo.IResourceRepository
import com.crowdproj.resources.logging.common.MpLoggerProvider

data class ResourcesCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val repoTest: IResourceRepository = IResourceRepository.NONE,
    val repoStub: IResourceRepository = IResourceRepository.NONE,
    val repoProd: IResourceRepository = IResourceRepository.NONE,
) {
    companion object {
        val NONE = ResourcesCorSettings()
    }
}