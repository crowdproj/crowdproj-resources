package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.config.ResourcesCorSettings
import com.crowdproj.resources.common.models.ResourcesCommand
import com.crowdproj.resources.common.models.ResourcesFilter
import com.crowdproj.resources.common.models.ResourcesState
import com.crowdproj.resources.common.models.ResourcesWorkMode
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.ResourceRepoStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizValidationSearchTest {

    private val command = ResourcesCommand.SEARCH
    private val settings by lazy {
        ResourcesCorSettings(
            repoTest = ResourceRepoStub()
        )
    }
    private val processor by lazy { ResourcesProcessor(settings) }

    @Test
    fun correctEmpty() = runTest {
        val ctx = ResourcesContext(
            command = command,
            state = ResourcesState.NONE,
            workMode = ResourcesWorkMode.TEST,
            resourceFilterRequest = ResourcesFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(ResourcesState.FAILING, ctx.state)
    }
}