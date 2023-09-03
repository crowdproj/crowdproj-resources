package ru.otus.otuskotlin.marketplace.mappers.v1.exceptions

import ru.otus.otuskotlin.marketplace.common.models.ResourcesCommand
class UnknownResourcesCommand(command: ResourcesCommand) : Throwable("Wrong command $command at mapping toTransport stage")