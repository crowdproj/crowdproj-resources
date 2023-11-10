package com.crowdproj.resources.mappers.v1

import kotlin.test.Test
import com.crowdproj.resources.api.v1.models.*
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.common.stubs.ResourcesStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = ResourceCreateRequest(
            debug = ResourceDebug(
                mode = ResourceRequestDebugMode.STUB,
                stub = ResourceRequestDebugStubs.SUCCESS,
            ),
            resource = ResourceCreateObject(
                resourceId = "1111",
                scheduleId = "2222",
                visible = ResourceVisibility.PUBLIC,
            ),
        )

        val context = ResourcesContext()
        context.fromApi(req)

        assertEquals(ResourcesStubs.SUCCESS, context.stubCase)
        assertEquals(ResourcesWorkMode.STUB, context.workMode)
        assertEquals("1111", context.resourceRequest.resourcesId.asString())
        assertEquals(ResourcesVisible.VISIBLE_PUBLIC, context.resourceRequest.visible)
    }

    @Test
    fun toTransport() {
        val context = ResourcesContext(
            requestId = ResourcesRequestId("1234"),
            command = ResourcesCommand.CREATE,
            resourceResponse = Resources(
                resourcesId = OtherResourcesId.NONE,
                scheduleId = ScheduleId.NONE,
                visible = ResourcesVisible.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                ResourcesError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = ResourcesState.RUNNING,
        )

        val req = context.toApi() as ResourceCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals(ResourceVisibility.PUBLIC, req.resource?.visible)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
    }
}
