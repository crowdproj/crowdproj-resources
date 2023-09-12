package ru.otus.otuskotlin.marketplace.biz

import ru.otus.otuskotlin.marketplace.common.*
import ru.otus.otuskotlin.marketplace.common.models.ResourcesCommand
import ru.otus.otuskotlin.marketplace.common.models.ResourcesWorkMode
import ru.otus.otuskotlin.marketplace.stubs.ResourcesStub

class ResourcesProcessor {
    suspend fun exec(ctx: ResourcesContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == ResourcesWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            ResourcesCommand.SEARCH -> {
                ctx.resourcesResponse.addAll(ResourcesStub.prepareSearchList("d-666-01", "111", "222"))
            }
            else -> {
                ctx.resourceResponse = ResourcesStub.get()
            }
        }
    }
}