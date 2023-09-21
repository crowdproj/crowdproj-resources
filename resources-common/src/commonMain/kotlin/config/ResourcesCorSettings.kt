package com.crowdproj.resources.common.config

import com.crowdproj.resources.common.repo.IResourceRepository

data class ResourcesCorSettings(
    val repoTest: IResourceRepository = IResourceRepository.NONE,
    val repoStub: IResourceRepository = IResourceRepository.NONE,
    val repoProd: IResourceRepository = IResourceRepository.NONE,
) {
    companion object {
        val NONE = ResourcesCorSettings()
    }
}