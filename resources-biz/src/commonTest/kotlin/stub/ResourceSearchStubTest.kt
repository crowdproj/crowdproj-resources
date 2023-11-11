package ru.otus.otuskotlin.marketplace.biz.stub.stub

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.stubs.ResourcesStubs
import ru.otus.otuskotlin.marketplace.stubs.ResourcesStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ResourceSearchStubTest {

    private val processor = ResourcesProcessor()
    private val filter = ResourcesFilter(searchString = "1111")

    @Test
    fun read() = runTest {

        val ctx = ResourcesContext(
            command = ResourcesCommand.SEARCH,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.SUCCESS,
            resourceFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.resourcesResponse.size > 1)
        val first = ctx.resourcesResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.resourcesId.toString().contains(filter.searchString))
        assertTrue(first.scheduleId.toString().contains(filter.searchString))
        with (ResourcesStub.get()) {
            assertEquals(visible, first.visible)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.SEARCH,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.BAD_ID,
            resourceFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Resources(), ctx.resourceResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.SEARCH,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.DB_ERROR,
            resourceFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Resources(), ctx.resourceResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.SEARCH,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.BAD_SEARCH_STRING,
            resourceFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Resources(), ctx.resourceResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
