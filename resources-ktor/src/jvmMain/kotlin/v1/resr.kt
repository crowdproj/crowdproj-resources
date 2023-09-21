package com.crowdproj.resources.app.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.crowdproj.resources.biz.ResourcesProcessor

fun Route.v1Resource(processor: ResourcesProcessor) {
    route("resource") {
        post("create") {
            call.createResource(processor)
        }
        post("read") {
            call.readResource(processor)
        }
        post("update") {
            call.updateResource(processor)
        }
        post("delete") {
            call.deleteResource(processor)
        }
        post("search") {
            call.searchResource(processor)
        }
    }
}