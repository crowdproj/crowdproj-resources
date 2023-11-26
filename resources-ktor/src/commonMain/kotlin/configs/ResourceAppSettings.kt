package com.crowdproj.resources.app.configs

import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.ResourcesCorSettings
import com.crowdproj.resources.logging.common.MpLoggerProvider

data class ResourceAppSettings(
    val corSettings: ResourcesCorSettings = ResourcesCorSettings(),
    val appUrls: List<String> = listOf(),
    val processor: ResourcesProcessor = ResourcesProcessor(corSettings),
    val logger: MpLoggerProvider = MpLoggerProvider(),
)