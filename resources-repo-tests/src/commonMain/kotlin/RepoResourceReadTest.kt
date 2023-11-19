package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.models.Resources
import com.crowdproj.resources.common.models.ResourcesId
import com.crowdproj.resources.common.repo.DbResourceIdRequest
import com.crowdproj.resources.common.repo.IResourceRepository
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoResourceReadTest {
    abstract val repo: IResourceRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readResource(DbResourceIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readResource(DbResourceIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitResources("delete") {
        override val initObjects: List<Resources> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = ResourcesId("resource-repo-read-notFound")

    }
}
