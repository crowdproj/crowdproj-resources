package com.crowdproj.resources.repo.inmemory

import com.crowdproj.resources.repo.tests.RepoResourceCreateTest
class ResRepoInMemoryCreateTest : RepoResourceCreateTest() {
    override val repo = ResourcesRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}