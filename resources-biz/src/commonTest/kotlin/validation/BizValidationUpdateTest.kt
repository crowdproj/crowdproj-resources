package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.config.ResourcesCorSettings
import com.crowdproj.resources.common.models.ResourcesCommand
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.ResourceRepoStub
import kotlin.test.Test


class BizValidationUpdateTest {

    private val command = ResourcesCommand.UPDATE
    private val settings by lazy {
        ResourcesCorSettings(
            repoTest = ResourceRepoStub()
        )
    }
    private val processor by lazy { ResourcesProcessor(settings) }

    @Test fun correctScheduleId() = validationScheduleIdCorrect(command, processor)
    @Test fun trimScheduleId() = validationScheduleIdTrim(command, processor)
    @Test fun emptyScheduleId() = validationScheduleIdEmpty(command, processor)
    @Test fun badSymbolsScheduleId() = validationScheduleIdFormat(command, processor)

    @Test fun correctOtherId() = validationOtherIdCorrect(command, processor)
    @Test fun trimOtherId() = validationOtherIdTrim(command, processor)
    @Test fun emptyOtherId() = validationOtherIdEmpty(command, processor)
    @Test fun badSymbolsOtherId() = validationOtherIdFormat(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

