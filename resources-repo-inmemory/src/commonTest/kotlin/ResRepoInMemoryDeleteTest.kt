package com.crowdproj.resources.repo.inmemory

import com.crowdproj.resources.common.repo.IResourceRepository
import com.crowdproj.resources.repo.tests.RepoResourceDeleteTest

class ResRepoInMemoryDeleteTest : RepoResourceDeleteTest() {
    override val repo: IResourceRepository = ResourcesRepoInMemory(
        initObjects = initObjects
    )
}