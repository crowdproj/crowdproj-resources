package ru.otus.otuskotlin.marketplace.app

import ru.otus.otuskotlin.marketplace.app.common.IResorcesAppSettings
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.ResourcesCorSettings

data class ResourcesAppSettings(
    val appUrls: List<String> = emptyList(),
    override val corSettings: ResourcesCorSettings,
    override val processor: ResourcesProcessor = ResourcesProcessor(corSettings),
): IResorcesAppSettings