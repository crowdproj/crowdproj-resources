package ru.otus.otuskotlin.marketplace.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.models.ResourcesCommand
import kotlin.test.Test

// TODO-validation-5: смотрим пример теста валидации, собранного из тестовых функций-оберток
@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationCreateTest {

    private val command = ResourcesCommand.CREATE
    private val processor by lazy { ResourcesProcessor() }

    @Test fun emptyOtherId() = validationOtherIdEmpty(command, processor)
    @Test fun otherIdIsNotNumber() = validationOtherIdIsNotNumber(command, processor)

    @Test fun emptyScheduleId() = validationScheduleIdEmpty(command, processor)
    @Test fun scheduleIdIsNotNumber() = validationScheduleIdIsNotNumber(command, processor)

}