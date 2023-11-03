package ru.otus.otuskotlin.marketplace.stubs

import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.stubs.ResourcesStubItem.RES_DEMAND_ITEM1

object ResourcesStub {
    fun get(): Resources = RES_DEMAND_ITEM1.copy()

    fun prepareResult(block: Resources.() -> Unit): Resources = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        resoucesDemand("d-666-01", filter),
        resoucesDemand("d-666-02", filter),
        resoucesDemand("d-666-03", filter),
        resoucesDemand("d-666-04", filter),
        resoucesDemand("d-666-05", filter),
        resoucesDemand("d-666-06", filter),
    )

    private fun resoucesDemand(id: String, filter: String) =
        resource(RES_DEMAND_ITEM1, id = id, filter = filter)

    private fun resource(base: Resources, id: String, filter: String) = base.copy(
        id = ResourcesId(id),
        resourcesId = OtherResourcesId("$filter $id"),
        scheduleId = ScheduleId("$filter $id"),
    )
}