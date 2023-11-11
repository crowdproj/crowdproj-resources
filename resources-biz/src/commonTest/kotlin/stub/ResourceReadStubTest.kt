package ru.otus.otuskotlin.marketplace.biz.stub.stub

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.stubs.ResourcesStubs
import ru.otus.otuskotlin.marketplace.stubs.ResourcesStub
import kotlin.test.Test
import kotlin.test.assertEquals

class ResourceReadStubTest {

    private val processor = ResourcesProcessor()
    private val id = ResourcesId("666")

    @Test
    fun read() = runTest {

        val ctx = ResourcesContext(
            command = ResourcesCommand.READ,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.SUCCESS,
            resourceRequest = Resources(
                id = id,
            ),
        )
        processor.exec(ctx)
        with (ResourcesStub.get()) {
            assertEquals(id, ctx.resourceResponse.id)
            assertEquals(resourcesId, ctx.resourceResponse.resourcesId)
            assertEquals(scheduleId, ctx.resourceResponse.scheduleId)
            assertEquals(visible, ctx.resourceResponse.visible)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.READ,
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
    fun databaseError() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.READ,
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
            command = ResourcesCommand.READ,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.BAD_SEARCH_STRING,
            resourceRequest = Resources(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Resources(), ctx.resourceResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}