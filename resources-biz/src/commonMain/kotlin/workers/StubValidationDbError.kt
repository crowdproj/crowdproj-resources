package ru.otus.otuskotlin.marketplace.biz.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.ResourcesError
import ru.otus.otuskotlin.marketplace.common.models.ResourcesState
import ru.otus.otuskotlin.marketplace.common.stubs.ResourcesStubs

fun ICorChainDsl<ResourcesContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == ResourcesStubs.DB_ERROR && state == ResourcesState.RUNNING }
    handle {
        state = ResourcesState.FAILING
        this.errors.add(
            ResourcesError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}