package ru.otus.otuskotlin.marketplace.biz.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.biz.ResourcesProcessor
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationIdCorrect(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-234-abc-ABC"),
            resourcesId = OtherResourcesId("111"),
            scheduleId = ScheduleId("222"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ResourcesState.FAILING, ctx.state)
}

fun validationIdTrim(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId(" \n\t 123-234-abc-ABC \n\t "),
            resourcesId = OtherResourcesId("111"),
            scheduleId = ScheduleId("222"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ResourcesState.FAILING, ctx.state)
}

fun validationIdEmpty(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId(""),
            resourcesId = OtherResourcesId("111"),
            scheduleId = ScheduleId("222"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ResourcesState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationIdFormat(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("!@#\$%^&*(),.{}"),
            resourcesId = OtherResourcesId("111"),
            scheduleId = ScheduleId("222"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ResourcesState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}