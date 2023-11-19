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
class ResourceDeleteStubTest {

    private val processor = ResourcesProcessor()
    val id = ResourcesId("666")

    @Test
    fun delete() = runTest {

        val ctx = ResourcesContext(
            command = ResourcesCommand.DELETE,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.SUCCESS,
            resourceRequest = Resources(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = CpwResourceStub.get()
        assertEquals(stub.id, ctx.resourceResponse.id)
        assertEquals(stub.resourcesId, ctx.resourceResponse.resourcesId)
        assertEquals(stub.scheduleId, ctx.resourceResponse.scheduleId)
        assertEquals(stub.visible, ctx.resourceResponse.visible)
    }

    @Test
    fun badId() = runTest {
        val ctx = ResourcesContext(
            command = ResourcesCommand.DELETE,
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
            command = ResourcesCommand.DELETE,
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
            command = ResourcesCommand.DELETE,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.STUB,
            stubCase = ResourcesStubs.CANNOT_DELETE,
            resourceRequest = Resources(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Resources(), ctx.resourceResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
