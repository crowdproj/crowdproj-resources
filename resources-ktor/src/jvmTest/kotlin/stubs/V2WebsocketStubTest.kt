package ru.otus.otuskotlin.marketplace.app.stubs

import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceCreateObject
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceCreateRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceDebug
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceDeleteObject
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceDeleteRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceInitResponse
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceReadObject
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceReadRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceRequestDebugMode
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceRequestDebugStubs
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceSearchFilter
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceSearchRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceUpdateObject
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceUpdateRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.ResourceVisibility
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.app.module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V2WebsocketStubTest {

    @Test
    fun createStub() {
        val request = ResourceCreateRequest(
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

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun readStub() {
        val request = ResourceReadRequest(
            requestId = "12345",
            resource = ResourceReadObject("666"),
            debug = ResourceDebug(
                mode = ResourceRequestDebugMode.STUB,
                stub = ResourceRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun updateStub() {
        val request = ResourceUpdateRequest(
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

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun deleteStub() {
        val request = ResourceDeleteRequest(
            requestId = "12345",
            resource = ResourceDeleteObject(
                id = "666",
            ),
            debug = ResourceDebug(
                mode = ResourceRequestDebugMode.STUB,
                stub = ResourceRequestDebugStubs.SUCCESS,
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun searchStub() {
        val request = ResourceSearchRequest(
            requestId = "12345",
            resourceFilter = ResourceSearchFilter(),
            debug = ResourceDebug(
                mode = ResourceRequestDebugMode.STUB,
                stub = ResourceRequestDebugStubs.SUCCESS,
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    private inline fun <reified T> testMethod(
        request: Any,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        application { module() }
        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/ws/v2") {
            withTimeout(3000) {
                val incame = incoming.receive() as Frame.Text
                val response = apiV2Mapper.decodeFromString<T>(incame.readText())
                assertIs<ResourceInitResponse>(response)
            }
            send(Frame.Text(apiV2Mapper.encodeToString(request)))
            withTimeout(3000) {
                val incame = incoming.receive() as Frame.Text
                val text = incame.readText()
                val response = apiV2Mapper.decodeFromString<T>(text)

                assertBlock(response)
            }
        }
    }
}