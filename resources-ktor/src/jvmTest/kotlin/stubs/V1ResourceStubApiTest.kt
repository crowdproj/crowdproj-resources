package ru.otus.otuskotlin.marketplace.app.stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import kotlin.test.assertEquals

class V1ResourceStubApiTest {
    @Test
    fun create() = testApplication {
        val client = myClient()

        val response = client.post("/v1/resource/create") {
            val requestObj = ResourceCreateRequest(
                requestId = "12345",
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
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<ResourceCreateResponse>()
        println(responseObj)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.resource?.id)
    }

    @Test
    fun read() = testApplication {
        val client = myClient()

        val response = client.post("/v1/resource/read") {
            val requestObj = ResourceReadRequest(
                requestId = "12345",
                resource = ResourceReadObject("666"),
                debug = ResourceDebug(
                    mode = ResourceRequestDebugMode.STUB,
                    stub = ResourceRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<ResourceReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.resource?.id)
    }

    @Test
    fun update() = testApplication {
        val client = myClient()

        val response = client.post("/v1/resource/update") {
            val requestObj = ResourceUpdateRequest(
                requestId = "12345",
                resource = ResourceUpdateObject(
                    id = "666",
                    resourceId = "111133",
                    scheduleId = "222233",
                    visible = ResourceVisibility.OWNER_ONLY,
                ),
                debug = ResourceDebug(
                    mode = ResourceRequestDebugMode.STUB,
                    stub = ResourceRequestDebugStubs.SUCCESS,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<ResourceUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.resource?.id)
    }

    @Test
    fun delete() = testApplication {
        val client = myClient()

        val response = client.post("/v1/resource/delete") {
            val requestObj = ResourceDeleteRequest(
                requestId = "12345",
                resource = ResourceDeleteObject(
                    id = "666",
                ),
                debug = ResourceDebug(
                    mode = ResourceRequestDebugMode.STUB,
                    stub = ResourceRequestDebugStubs.SUCCESS,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<ResourceDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.resource?.id)
    }

    @Test
    fun search() = testApplication {
        val client = myClient()

        val response = client.post("/v1/resource/search") {
            val requestObj = ResourceSearchRequest(
                requestId = "12345",
                resourceFilter = ResourceSearchFilter(),
                debug = ResourceDebug(
                    mode = ResourceRequestDebugMode.STUB,
                    stub = ResourceRequestDebugStubs.SUCCESS,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<ResourceSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.resources?.first()?.id)
    }


    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }
}