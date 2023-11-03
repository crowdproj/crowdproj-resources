package ru.otus.otuskotlin.marketplace.app.common

import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.ResourcesCorSettings

interface IResorcesAppSettings {
    val processor: ResourcesProcessor
    val corSettings: ResourcesCorSettings
}