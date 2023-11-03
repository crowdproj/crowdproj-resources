package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.otuskotlin.marketplace.common.helpers.errorValidation
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.helpers.fail
import ru.otus.otuskotlin.marketplace.common.models.OtherResourcesId

fun ICorChainDsl<ResourcesContext>.validateOtherIdNotEmpty(title: String) = worker {
    this.title = title
    on { resourceValidating.resourcesId.asString() == ""  }
    handle {
        fail(
            errorValidation(
                field = "OtherResourcesId",
                violationCode = "noContent",
                description = "field must be filled in"
            )
        )
    }
}