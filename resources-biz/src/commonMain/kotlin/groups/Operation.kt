package ru.otus.otuskotlin.marketplace.biz.groups

import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.ResourcesCommand
import ru.otus.otuskotlin.marketplace.common.models.ResourcesState
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.chain

fun ICorChainDsl<ResourcesContext>.operation(title: String, command: ResourcesCommand, block: ICorChainDsl<ResourcesContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == ResourcesState.RUNNING }
}