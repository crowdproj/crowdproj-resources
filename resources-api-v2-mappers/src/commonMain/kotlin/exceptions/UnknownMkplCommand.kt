package ru.otus.otuskotlin.marketplace.mappers.v2.exceptions

import ru.otus.otuskotlin.marketplace.common.models.ResourcesCommand

class UnknownMkplCommand(command: ResourcesCommand) : Throwable("Wrong command $command at mapping toTransport stage")
