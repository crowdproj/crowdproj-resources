package ru.otus.otuskotlin.marketplace.stubs

import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.stubs.ResourcesStubBolts.AD_DEMAND_BOLT1

object ResourcesStub {
    fun get(): Resources = AD_DEMAND_BOLT1.copy()

    fun prepareResult(block: Resources.() -> Unit): Resources = get().apply(block)

    fun prepareSearchList(id: String, otherId: String, scheduleId: String) = listOf(
        resoucesDemand("d-666-01", otherId, scheduleId),
        resoucesDemand("d-666-02", otherId, scheduleId),
        resoucesDemand("d-666-03", otherId, scheduleId),
        resoucesDemand("d-666-04", otherId, scheduleId),
        resoucesDemand("d-666-05", otherId, scheduleId),
        resoucesDemand("d-666-06", otherId, scheduleId),
    )

    private fun resoucesDemand(id: String, otherId: String, scheduleId: String) =
        resource(AD_DEMAND_BOLT1, id = id, otherId = otherId, scheduleId = scheduleId)

    private fun resource(base: Resources, id: String, otherId: String, scheduleId: String) = base.copy(
        id = ResourcesId(id),
        resourcesId = OtherResourcesId(otherId),
        scheduleId = ScheduleId(scheduleId),
    )

}