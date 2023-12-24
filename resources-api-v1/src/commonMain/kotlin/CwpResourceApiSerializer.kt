package com.crowdproj.resources.api.v1

import com.crowdproj.resources.api.v1.models.IRequestResource
import com.crowdproj.resources.api.v1.models.IResponseResource
import kotlinx.serialization.json.Json

fun encodeRequest(request: IRequestResource): String =
    Json.encodeToString(IRequestResource.serializer(), request)

fun decodeRequest(jsonStr: String): IRequestResource =
    Json.decodeFromString(IRequestResource.serializer(), jsonStr)

fun encodeResponse(response: IResponseResource): String =
    Json.encodeToString(IResponseResource.serializer(), response)

fun decodeResponse(jsonStr: String): IResponseResource =
    Json.decodeFromString(IResponseResource.serializer(), jsonStr)