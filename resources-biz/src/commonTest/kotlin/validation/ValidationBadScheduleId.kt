package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.*
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationScheduleIdCorrect(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-abc"),
            resourcesId = OtherResourcesId("111"),
            scheduleId = ScheduleId("222"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ResourcesState.FAILING, ctx.state)
}


fun validationScheduleIdTrim(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-abc"),
            resourcesId = OtherResourcesId("111"),
            scheduleId = ScheduleId(" \n\t 222 \n\t "),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ResourcesState.FAILING, ctx.state)
}


fun validationScheduleIdEmpty(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-abc"),
            resourcesId = OtherResourcesId("111"),
            scheduleId = ScheduleId(""),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ResourcesState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("scheduleId", error?.field)
    assertContains(error?.message ?: "", "scheduleId")
}


fun validationScheduleIdFormat(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-abc"),
            resourcesId = OtherResourcesId("111"),
            scheduleId = ScheduleId("!@#\$%^&*(),.{}"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ResourcesState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("scheduleId", error?.field)
    assertContains(error?.message ?: "", "scheduleId")
}
