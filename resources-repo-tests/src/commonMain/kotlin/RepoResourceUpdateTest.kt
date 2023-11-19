package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.common.repo.DbResourceRequest
import com.crowdproj.resources.common.repo.IResourceRepository
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoResourceUpdateTest {
    abstract val repo: IResourceRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = ResourcesId("resource-repo-update-not-found")
    protected val lockBad = ResourcesLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = ResourcesLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        Resources(
            id = updateSucc.id,
            resourcesId = OtherResourcesId("1111"),
            scheduleId = ScheduleId("2222"),
            ownerId = ResourcesUserId("owner-123"),
            visible = ResourcesVisible.VISIBLE_TO_GROUP,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = Resources(
        id = updateIdNotFound,
        resourcesId = OtherResourcesId("1111"),
        scheduleId = ScheduleId("2222"),
        ownerId = ResourcesUserId("owner-123"),
        visible = ResourcesVisible.VISIBLE_TO_GROUP,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        Resources(
            id = updateConc.id,
            resourcesId = OtherResourcesId("1111"),
            scheduleId = ScheduleId("2222"),
            ownerId = ResourcesUserId("owner-123"),
            visible = ResourcesVisible.VISIBLE_TO_GROUP,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateResource(DbResourceRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.resourcesId, result.data?.resourcesId)
        assertEquals(reqUpdateSucc.resourcesId, result.data?.resourcesId)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateResource(DbResourceRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateResource(DbResourceRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitResources("update") {
        override val initObjects: List<Resources> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
