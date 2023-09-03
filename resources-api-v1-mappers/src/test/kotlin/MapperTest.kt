package ru.otus.otuskotlin.marketplace.mappers.v1

import org.junit.Test
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.stubs.ResourcesStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = ResourceCreateRequest(
            requestId = "1234",
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
        context.fromTransport(req)

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

        val req = context.toTransportAd() as ResourceCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals(ResourceVisibility.PUBLIC, req.resource?.visible)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
