package com.crowdproj.resources.logging.logback

import ch.qos.logback.classic.Logger
import com.crowdproj.resources.logging.common.IMpLogWrapper
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun resourceLoggerLogback(logger: Logger): IMpLogWrapper = CwpLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun resourceLoggerLogback(clazz: KClass<*>): IMpLogWrapper =
    resourceLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)

@Suppress("unused")
fun resourceLoggerLogback(loggerId: String): IMpLogWrapper =
    resourceLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)