package ru.otus.otuskotlin.marketplace.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = ResourcesCommand.SEARCH
    private val processor by lazy { ResourcesProcessor() }

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