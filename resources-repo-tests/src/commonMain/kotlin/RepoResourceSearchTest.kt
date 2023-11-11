package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.common.repo.DbResourceFilterRequest
import com.crowdproj.resources.common.repo.IResourceRepository
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoResourceSearchTest {
    abstract val repo: IResourceRepository

    protected open val initializedObjects: List<Resources> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchResource(DbResourceFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitResources("search") {

        val searchOwnerId = ResourcesUserId("owner-124")
        override val initObjects: List<Resources> = listOf(
            createInitTestModel("rs1"),
            createInitTestModel("rs2", ownerId = searchOwnerId),
            createInitTestModel("rs3", ownerId = searchOwnerId),
        )
    }
}
