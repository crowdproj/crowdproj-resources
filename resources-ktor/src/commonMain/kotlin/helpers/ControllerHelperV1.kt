package com.crowdproj.resources.app.helpers

import com.crowdproj.resources.mappers.v1.fromApi
import com.crowdproj.resources.mappers.v1.toApi
import com.crowdproj.resources.api.v1.models.IRequestResource
import com.crowdproj.resources.api.v1.models.IResponseResource
import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.helpers.asResourcesError
import com.crowdproj.resources.common.helpers.fail
import com.crowdproj.resources.common.models.ResourcesRequestId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.utils.io.charsets.*
import kotlinx.datetime.Clock

suspend inline fun <reified Rq : IRequestResource, reified Rs : IResponseResource> ApplicationCall.controllerHelperV1(
    appConfig: ResourceAppSettings
) {
    val endpoint: String = this.request.local.localAddress
    val requestId = this.callId
    val logger = application.log
    suitableCharset(Charsets.UTF_8)
    defaultTextContentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
    val ctx = ResourcesContext(
        timeStart = Clock.System.now(),
        requestId = requestId?.let { ResourcesRequestId(it) } ?: ResourcesRequestId.NONE,
    )
    try {
        logger.info("Started $endpoint request $requestId")
        val reqData = this.receive<Rq>()
        ctx.fromApi(reqData)
        appConfig.processor.exec(ctx)
        respond<Rs>(ctx.toApi() as Rs)
        logger.info("Finished $endpoint request $requestId")
    } catch (e: BadRequestException) {
        logger.error(
            "Bad request $requestId at $endpoint with exception", e
        )
        ctx.fail(
            e.asResourcesError(
                code = "bad-request",
                group = "bad-request",
                message = "The request is not correct due to wrong/missing request parameters, body content or header values"
            )
        )
        appConfig.processor.exec(ctx)
        respond<Rs>(ctx.toApi() as Rs)
    } catch (e: Throwable) {
        logger.error(
            "Fail to handle $endpoint request $requestId with exception", e
        )
        ctx.fail(
            e.asResourcesError(
                message = "Unknown error. We have been informed and dealing with the problem."
            )
        )
        appConfig.processor.exec(ctx)
        respond<Rs>(ctx.toApi() as Rs)
    }
}
