package com.crowdproj.resources.repo.inmemory

import com.crowdproj.resources.common.repo.IResourceRepository
import com.crowdproj.resources.repo.tests.RepoResourceUpdateTest

class ResRepoInMemoryUpdateTest : RepoResourceUpdateTest() {
    override val repo: IResourceRepository = ResourcesRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}