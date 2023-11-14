package ru.otus.otuskotlin.marketplace.biz.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

fun validationScheduleIdEmpty(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-234-abc-ABC"),
            resourcesId = OtherResourcesId("111"),
            scheduleId = ScheduleId(""),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(2, ctx.errors.size)
    assertEquals(ResourcesState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("ScheduleId", error?.field)
    assertContains(error?.message ?: "", "ScheduleId")
}

fun validationScheduleIdIsNotNumber(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-234-abc-ABC"),
            resourcesId = OtherResourcesId("111"),
            scheduleId = ScheduleId("qqqwe"),
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