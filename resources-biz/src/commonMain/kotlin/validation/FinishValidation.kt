package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.ResourcesState
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker

fun ICorChainDsl<ResourcesContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == ResourcesState.RUNNING }
    handle {
        resourceValidated = resourceValidating
    }
}

fun ICorChainDsl<ResourcesContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == ResourcesState.RUNNING }
    handle {
        resourceFilterValidated = resourceFilterValidating
    }
}