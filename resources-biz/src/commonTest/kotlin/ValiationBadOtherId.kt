package ru.otus.otuskotlin.marketplace.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationOtherIdEmpty(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-234-abc-ABC"),
            resourcesId = OtherResourcesId(""),
            scheduleId = ScheduleId("222"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(2, ctx.errors.size)
    assertEquals(ResourcesState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("OtherResourcesId", error?.field)
    assertContains(error?.message ?: "", "OtherResourcesId")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationOtherIdIsNotNumber(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-234-abc-ABC"),
            resourcesId = OtherResourcesId("qqq"),
            scheduleId = ScheduleId("222"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ResourcesState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("OtherResourcesId", error?.field)
    assertContains(error?.message ?: "", "OtherResourcesId")
}