package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.common.repo.DbResourceRequest
import com.crowdproj.resources.common.repo.IResourceRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


abstract class RepoResourceCreateTest {
    abstract val repo: IResourceRepository

    protected open val lockNew: ResourcesLock = ResourcesLock("20000000-0000-0000-0000-000000000002")

    private val createObj = Resources(
        resourcesId = OtherResourcesId("1111"),
        scheduleId = ScheduleId("2222"),
        ownerId = ResourcesUserId("owner-123"),
        visible = ResourcesVisible.VISIBLE_TO_GROUP,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createResource(DbResourceRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: ResourcesId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.resourcesId, result.data?.resourcesId)
        assertEquals(expected.scheduleId, result.data?.scheduleId)
        assertNotEquals(ResourcesId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitResources("create") {
        override val initObjects: List<Resources> = emptyList()
    }
}