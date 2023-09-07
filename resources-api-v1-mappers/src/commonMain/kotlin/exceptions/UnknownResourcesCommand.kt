package com.crowdproj.resources.mappers.v1.exceptions

import com.crowdproj.resources.common.models.ResourcesCommand
class UnknownResourcesCommand(command: ResourcesCommand) : Throwable("Wrong command $command at mapping toTransport stage")