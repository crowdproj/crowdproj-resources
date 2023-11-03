package ru.otus.otuskotlin.marketplace.api.v1

import ru.otus.otuskotlin.marketplace.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = ResourceCreateRequest(
        requestId = "123",
        debug = ResourceDebug(
            mode = ResourceRequestDebugMode.STUB,
            stub = ResourceRequestDebugStubs.BAD_OTHER_ID
        ),
        resource = ResourceCreateObject(
            resourceId = "1111",
            scheduleId = "2222",
            ownerId = "3333",
            visible = ResourceVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"resourceId\":\\s*\"1111\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badOtherId\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as ResourceCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, ResourceCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}