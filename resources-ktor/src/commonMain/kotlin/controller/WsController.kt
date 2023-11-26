package com.crowdproj.resources.app.controller

import com.crowdproj.resources.api.logs.mapper.toLog
import com.crowdproj.resources.api.v1.decodeRequest
import com.crowdproj.resources.api.v1.encodeResponse
import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.helpers.addError
import com.crowdproj.resources.common.helpers.asResourcesError
import com.crowdproj.resources.common.helpers.isUpdatableCommand
import com.crowdproj.resources.mappers.v1.fromApi
import com.crowdproj.resources.mappers.v1.toApi
import com.crowdproj.resources.mappers.v1.toTransportInit
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow

val sessions = mutableSetOf<WebSocketSession>()

private val clazzWS = WebSocketSession::wsHandlerV1::class.qualifiedName ?: "wsHandlerV1_"

suspend fun WebSocketSession.wsHandlerV1(appSettings: ResourceAppSettings) {
    sessions.add(this)

    // Handle init request
    val ctx = ResourcesContext()
    val init = encodeResponse(ctx.toTransportInit())
    outgoing.send(Frame.Text(init))

    // Handle flow
    incoming.receiveAsFlow().mapNotNull { it ->
        val frame = it as? Frame.Text ?: return@mapNotNull

        val jsonStr = frame.readText()
        val context = ResourcesContext()

        // Handle without flow destruction
        try {
            val request = decodeRequest(jsonStr)

            val logId = clazzWS
            val logger = appSettings.corSettings.loggerProvider.logger(logId)
            logger.doWithLogging(logId) {
                context.fromApi(request)

                logger.info(
                    msg = "${context.command} request is got",
                    data = context.toLog("${logId}-request"),
                )

                appSettings.processor.exec(context)

                val result = encodeResponse(context.toApi())

                // If change request, response is sent to everyone
                if (context.isUpdatableCommand()) {
                    sessions.forEach {
                        result.let { it1 -> Frame.Text(it1) }.let { it2 -> it.send(it2) }
                    }
                } else {
                    result.let { it1 -> Frame.Text(it1) }.let { it2 -> outgoing.send(it2) }
                }

                logger.info(
                    msg = "${context.command} response is sent",
                    data = context.toLog("${logId}-response")
                )
            }
        } catch (_: ClosedReceiveChannelException) {
            sessions.clear()
        } catch (t: Throwable) {
            context.addError(t.asResourcesError())

            val result = encodeResponse(context.toTransportInit())
            outgoing.send(Frame.Text(result))
        }
    }.collect()

    sessions.remove(this)
}