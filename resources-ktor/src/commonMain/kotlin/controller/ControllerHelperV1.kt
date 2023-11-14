package com.crowdproj.resources.app.controller

import com.crowdproj.resources.api.logs.mapper.toLog
import com.crowdproj.resources.api.v1.models.IRequestResource
import com.crowdproj.resources.api.v1.models.IResponseResource
import com.crowdproj.resources.app.configs.ResourceAppSettings
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.helpers.asResourcesError
import com.crowdproj.resources.common.models.ResourcesCommand
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.logging.common.IMpLogWrapper
import com.crowdproj.resources.mappers.v1.fromApi
import com.crowdproj.resources.mappers.v1.toApi
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock

suspend inline fun <reified Rq : IRequestResource, reified Rs : IResponseResource> ApplicationCall.processV1(
    appSettings: ResourceAppSettings,
    logger: IMpLogWrapper,
    logId: String,
    command: ResourcesCommand? = null,
) {
    val ctx = ResourcesContext(
        timeStart = Clock.System.now(),
    )
    val processor = appSettings.processor
    try {
        logger.doWithLogging(id = logId) {
            val request = this.receive<Rq>()
            ctx.fromApi(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            processor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            respond(ctx.toApi() as Rs)
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            command?.also { ctx.command = it }
            logger.error(
                msg = "$command handling failed",
            )
            ctx.state = ResourcesState.FAILING
            ctx.errors.add(e.asResourcesError())
            processor.exec(ctx)
            respond(ctx.toApi() as Rs)
        }
    }
}