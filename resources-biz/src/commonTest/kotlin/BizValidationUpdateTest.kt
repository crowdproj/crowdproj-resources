package ru.otus.otuskotlin.marketplace.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.models.ResourcesCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = ResourcesCommand.UPDATE
    private val processor by lazy { ResourcesProcessor() }

    @Test fun emptyOtherId() = validationOtherIdEmpty(command, processor)
    @Test fun otherIdIsNotNumber() = validationOtherIdIsNotNumber(command, processor)

    @Test fun emptyScheduleId() = validationScheduleIdEmpty(command, processor)
    @Test fun scheduleIdIsNotNumber() = validationScheduleIdIsNotNumber(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}