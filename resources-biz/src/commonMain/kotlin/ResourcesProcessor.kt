package com.crowdproj.resources.biz

import com.crowdproj.resources.common.*
import com.crowdproj.resources.common.config.ResourcesCorSettings
import com.crowdproj.resources.common.models.ResourcesCommand
import com.crowdproj.resources.common.models.ResourcesWorkMode
import com.crowdproj.resources.stubs.CpwResourceStub

class ResourcesProcessor(private val settings: ResourcesCorSettings = ResourcesCorSettings()) {
    //TODO переделать под процессор AD
    suspend fun exec(ctx: ResourcesContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == ResourcesWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            ResourcesCommand.SEARCH -> {
                ctx.resourcesResponse.addAll(CpwResourceStub.prepareSearchList("d-666-01", "111", "222"))
            }
            else -> {
                ctx.resourceResponse = CpwResourceStub.get()
            }
        }
    }
}