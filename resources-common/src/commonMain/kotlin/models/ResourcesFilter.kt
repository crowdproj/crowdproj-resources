package com.crowdproj.resources.common.models

data class ResourcesFilter(
    var searchString: String = "",
    var ownerId: ResourcesUserId = ResourcesUserId.NONE,
)