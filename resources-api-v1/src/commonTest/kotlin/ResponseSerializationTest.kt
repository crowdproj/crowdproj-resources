package com.crowdproj.resources.api.v1

import com.crowdproj.resources.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = ResourceCreateResponse(
        requestId = "123",
        resource = ResourceResponseObject(
            resourceId = "1111",
            scheduleId = "2222",
            ownerId = "3333",
            visible = ResourceVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = encodeResponse(response)

        println(json)

        assertContains(json, Regex("\"resourceId\":\\s*\"1111\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = encodeResponse(response)
        val obj = decodeResponse(json) as ResourceCreateResponse

        assertEquals(response, obj)
    }
}