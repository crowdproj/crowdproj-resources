package com.crowdproj.resources.common.helpers

import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.resources.common.models.ResourcesCommand

fun ResourcesContext.isUpdatableCommand() =
    this.command in listOf(ResourcesCommand.CREATE, ResourcesCommand.UPDATE, ResourcesCommand.DELETE)