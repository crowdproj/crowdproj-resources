package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.config.ResourcesCorSettings
import com.crowdproj.resources.common.models.ResourcesCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.ResourceRepoStub
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = ResourcesCommand.DELETE
    private val settings by lazy {
        ResourcesCorSettings(
            repoTest = ResourceRepoStub()
        )
    }
    private val processor by lazy { ResourcesProcessor(settings) }

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}