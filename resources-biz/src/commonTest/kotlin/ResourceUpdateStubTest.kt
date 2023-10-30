package ru.otus.otuskotlin.marketplace.biz.stub

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.stubs.ResourcesStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class ResourceUpdateStubTest {

    private val processor = ResourcesProcessor()
    private val id = ResourcesId("777")
    private val resourcesId = OtherResourcesId("1122")
    private val scheduleId = ScheduleId("3333")
    private val visible = ResourcesVisible.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {

        val ctx = ResourcesContext(
            command = ResourcesCommand.UPDATE,
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
        assertEquals(id, ctx.resourceResponse.id)
        assertEquals(resourcesId, ctx.resourceResponse.resourcesId)
        assertEquals(scheduleId, ctx.resourceResponse.scheduleId)
        assertEquals(visible, ctx.resourceResponse.visible)
    }

    @Test
    fun badId() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.UPDATE,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.BAD_ID,
            resourceRequest = Resources(),
        )
        processor.exec(ctx)
        assertEquals(Resources(), ctx.resourceResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badOtherId() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.UPDATE,
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
            command = ResourcesCommand.UPDATE,
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
            command = ResourcesCommand.UPDATE,
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
            command = ResourcesCommand.UPDATE,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.BAD_SEARCH_STRING,
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