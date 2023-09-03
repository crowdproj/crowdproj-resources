package ru.otus.otuskotlin.marketplace.common.helpers

import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.models.ResourcesCommand

fun ResourcesContext.isUpdatableCommand() =
    this.command in listOf(ResourcesCommand.CREATE, ResourcesCommand.UPDATE, ResourcesCommand.DELETE)