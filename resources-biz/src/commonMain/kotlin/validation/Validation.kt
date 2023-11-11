package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.ResourcesState
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.chain

fun ICorChainDsl<ResourcesContext>.validation(block: ICorChainDsl<ResourcesContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == ResourcesState.RUNNING }
}