package ru.otus.otuskotlin.marketplace.app.stubs

import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.app.moduleJvm
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V1WebsocketStubTest {

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
        application { moduleJvm() }
        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/ws/v1") {
            withTimeout(3000) {
                val incame = incoming.receive() as Frame.Text
                val response = apiV1Mapper.readValue(incame.readText(), T::class.java)
                assertIs<ResourceInitResponse>(response)
            }
            send(Frame.Text(apiV1Mapper.writeValueAsString(request)))
            withTimeout(3000) {
                val incame = incoming.receive() as Frame.Text
                val response = apiV1Mapper.readValue(incame.readText(), T::class.java)

                assertBlock(response)
            }
        }
    }
}