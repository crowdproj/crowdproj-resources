package ru.otus.otuskotlin.marketplace.biz.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.ResourcesId
import ru.otus.otuskotlin.marketplace.common.models.ResourcesState
import ru.otus.otuskotlin.marketplace.common.stubs.ResourcesStubs
import ru.otus.otuskotlin.marketplace.stubs.ResourcesStub

fun ICorChainDsl<ResourcesContext>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == ResourcesStubs.SUCCESS && state == ResourcesState.RUNNING }
    handle {
        state = ResourcesState.FINISHING
        val stub = ResourcesStub.prepareResult {
            resourceRequest.id.takeIf { it != ResourcesId.NONE }?.also { this.id = it }
        }
        resourceResponse = stub
    }
}