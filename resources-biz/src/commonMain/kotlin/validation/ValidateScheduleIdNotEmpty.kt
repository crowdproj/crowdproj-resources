package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.otuskotlin.marketplace.common.helpers.errorValidation
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.helpers.fail
import ru.otus.otuskotlin.marketplace.common.models.ScheduleId

fun ICorChainDsl<ResourcesContext>.validateScheduleIdNotEmpty(title: String) = worker {
    this.title = title
    on { resourceValidating.scheduleId == ScheduleId.NONE || resourceValidating.scheduleId.asString() == "" }
    handle {
        fail(
            errorValidation(
                field = "ScheduleId",
                violationCode = "noContent",
                description = "field must be filled in"
            )
        )
    }
}