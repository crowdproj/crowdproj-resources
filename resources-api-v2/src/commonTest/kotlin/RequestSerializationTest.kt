package ru.otus.otuskotlin.marketplace.api.v2

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request: IRequest = ResourceCreateRequest(
        requestType = "create",
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
        val json = apiV2Mapper.encodeToString(request)

        println(json)

        assertContains(json, Regex("\"resourceId\":\\s*\"1111\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badOtherId\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(request)
        val obj = apiV2Mapper.decodeFromString(json) as ResourceCreateRequest

        assertEquals(request, obj)
    }
    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiV2Mapper.decodeFromString<ResourceCreateRequest>(jsonString)

        assertEquals("123", obj.requestId)
    }
}