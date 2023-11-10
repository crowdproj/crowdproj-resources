package com.crowdproj.resources.api.v1

import com.crowdproj.resources.api.v1.models.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = ResourceCreateRequest(
        debug = ResourceDebug(
            mode = ResourceRequestDebugMode.STUB,
            stub = ResourceRequestDebugStubs.SUCCESS
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
        val json = Json.encodeToString(IRequestResource.serializer(), request)

        println(json)

        assertContains(json, Regex("\"resourceId\":\\s*\"1111\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = Json.encodeToString(IRequestResource.serializer(), request)
        val obj = Json.decodeFromString(IRequestResource.serializer(), json) as ResourceCreateRequest

        assertEquals(request, obj)
    }
}