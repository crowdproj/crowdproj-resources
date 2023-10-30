package ru.otus.otuskotlin.marketplace.common

import ru.otus.otuskotlin.marketplace.logging.common.MpLoggerProvider

data class ResourcesCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
) {
    companion object {
        val NONE = ResourcesCorSettings()
    }
}