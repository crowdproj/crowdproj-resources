package com.crowdproj.resources.app.configs

import com.crowdproj.resources.api.v1.apiV1Mapper
import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.config.ResourcesCorSettings
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import kotlinx.serialization.json.Json

data class ResourceAppSettings(
    var clientEngine: HttpClientEngine = CIO.create(),
    val json: Json = apiV1Mapper,
    val corSettings: ResourcesCorSettings = ResourcesCorSettings(),
    val appUrls: List<String> = listOf(),
    val processor: ResourcesProcessor = ResourcesProcessor(corSettings)
)