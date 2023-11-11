package ru.otus.otuskotlin.marketplace.biz.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.ResourcesState

fun ICorChainDsl<ResourcesContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == ResourcesState.NONE }
    handle { state = ResourcesState.RUNNING }
}