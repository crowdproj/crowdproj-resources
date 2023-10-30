package ru.otus.otuskotlin.marketplace.biz.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.helpers.fail
import ru.otus.otuskotlin.marketplace.common.models.ResourcesError
import ru.otus.otuskotlin.marketplace.common.models.ResourcesState

fun ICorChainDsl<ResourcesContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == ResourcesState.RUNNING }
    handle {
        fail(
            ResourcesError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}