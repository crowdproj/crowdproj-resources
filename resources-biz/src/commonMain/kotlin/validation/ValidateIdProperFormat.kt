package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.otuskotlin.marketplace.common.helpers.errorValidation
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.helpers.fail
import ru.otus.otuskotlin.marketplace.common.models.ResourcesId

fun ICorChainDsl<ResourcesContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в ResourcesId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { resourceValidating.id != ResourcesId.NONE && ! resourceValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = resourceValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}