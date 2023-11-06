package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.api.v1.models.*
import com.crowdproj.resources.app.helpers.controllerHelperV1
import com.crowdproj.resources.app.configs.ResourceAppSettings
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.reflect.KClass

private val clazz: KClass<*> = Application::configureRouting::class
@OptIn(ExperimentalEncodingApi::class)
fun Application.configureRouting(appConfig: ResourceAppSettings) {
    initRest(appConfig)
    initCors(appConfig)
    routing {
//        trace { application.log.trace(it.buildText()) }

        swagger(appConfig)

        post("v1/create") {
            call.controllerHelperV1<ResourceCreateRequest, ResourceCreateResponse>(appConfig)
        }
        post("v1/read") {
            call.controllerHelperV1<ResourceReadRequest, ResourceReadResponse>(appConfig)
        }
        post("v1/update") {
            call.controllerHelperV1<ResourceUpdateRequest, ResourceUpdateResponse>(appConfig)
        }
        post("v1/delete") {
            call.controllerHelperV1<ResourceDeleteRequest, ResourceDeleteResponse>(appConfig)
        }
        post("v1/search") {
            call.controllerHelperV1<ResourceSearchRequest, ResourceSearchResponse>(appConfig)
        }
    }
}