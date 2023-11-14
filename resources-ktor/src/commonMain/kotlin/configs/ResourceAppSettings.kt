package com.crowdproj.resources.app.configs

import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.config.ResourcesCorSettings

data class ResourceAppSettings(
    val corSettings: ResourcesCorSettings = ResourcesCorSettings(),
    val appUrls: List<String> = listOf(),
    val processor: ResourcesProcessor = ResourcesProcessor(corSettings)
)