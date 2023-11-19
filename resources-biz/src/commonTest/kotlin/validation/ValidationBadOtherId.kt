package com.crowdproj.resources.biz.validation

import com.crowdproj.resources.biz.ResourcesProcessor
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationOtherIdCorrect(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
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

@OptIn(ExperimentalCoroutinesApi::class)
fun validationOtherIdTrim(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-abc"),
            resourcesId = OtherResourcesId(" \n\t 111 \n\t "),
            scheduleId = ScheduleId("222"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ResourcesState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationOtherIdEmpty(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-abc"),
            resourcesId = OtherResourcesId(""),
            scheduleId = ScheduleId("222"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ResourcesState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("resourcesId", error?.field)
    assertContains(error?.message ?: "", "resourcesId")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationOtherIdFormat(command: ResourcesCommand, processor: ResourcesProcessor) = runTest {
    val ctx = ResourcesContext(
        command = command,
        state = ResourcesState.NONE,
        workMode = ResourcesWorkMode.TEST,
        resourceRequest = Resources(
            id = ResourcesId("123-abc"),
            resourcesId = OtherResourcesId("!@#\$%^&*(),.{}"),
            scheduleId = ScheduleId("222"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ResourcesState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("resourcesId", error?.field)
    assertContains(error?.message ?: "", "resourcesId")
}
