package com.crowdproj.resources.biz.stub

import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.common.stubs.ResourcesStubs
import com.crowdproj.resources.stubs.CpwResourceStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ResourceCreateStubTest {

    private val processor = ResourcesProcessor()
    val id = ResourcesId("666")
    val resourcesId = OtherResourcesId("resourcesId 666")
    val scheduleId = ScheduleId("scheduleId 666")
    val visible = ResourcesVisible.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {

        val ctx = ResourcesContext(
            command = ResourcesCommand.CREATE,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.SUCCESS,
            resourceRequest = Resources(
                id = id,
                resourcesId = resourcesId,
                scheduleId = scheduleId,
                visible = visible,
            ),
        )
        processor.exec(ctx)
        assertEquals(CpwResourceStub.get().id, ctx.resourceResponse.id)
        assertEquals(resourcesId, ctx.resourceResponse.resourcesId)
        assertEquals(scheduleId, ctx.resourceResponse.scheduleId)
        assertEquals(visible, ctx.resourceResponse.visible)
    }

    @Test
    fun badOtherResourcesId() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.CREATE,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.BAD_OTHER_ID,
            resourceRequest = Resources(
                id = id,
                resourcesId = OtherResourcesId(""),
                scheduleId = scheduleId,
                visible = visible,
            ),
        )
        processor.exec(ctx)
        assertEquals(Resources(), ctx.resourceResponse)
        assertEquals("resourcesId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badScheduleId() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.CREATE,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.BAD_SCHEDULE_ID,
            resourceRequest = Resources(
                id = id,
                resourcesId = resourcesId,
                scheduleId = ScheduleId(""),
                visible = visible,
            ),
        )
        processor.exec(ctx)
        assertEquals(Resources(), ctx.resourceResponse)
        assertEquals("scheduleId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.CREATE,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.DB_ERROR,
            resourceRequest = Resources(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Resources(), ctx.resourceResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.CREATE,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.BAD_ID,
            resourceRequest = Resources(
                id = id,
                resourcesId = resourcesId,
                scheduleId = scheduleId,
                visible = visible,
            ),
        )
        processor.exec(ctx)
        assertEquals(Resources(), ctx.resourceResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
