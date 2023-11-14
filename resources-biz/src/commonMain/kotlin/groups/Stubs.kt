package ru.otus.otuskotlin.marketplace.biz.groups

import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.ResourcesState
import ru.otus.otuskotlin.marketplace.common.models.ResourcesWorkMode
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.chain

fun ICorChainDsl<ResourcesContext>.stubs(title: String, block: ICorChainDsl<ResourcesContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == ResourcesWorkMode.STUB && state == ResourcesState.RUNNING }
}