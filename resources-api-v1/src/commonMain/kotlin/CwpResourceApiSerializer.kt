package com.crowdproj.resources.api.v1

import kotlinx.serialization.json.Json

val apiV1Mapper = Json {
    ignoreUnknownKeys = true
}