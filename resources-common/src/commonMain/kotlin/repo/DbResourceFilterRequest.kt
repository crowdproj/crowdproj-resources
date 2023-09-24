package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.models.ResourcesUserId

data class DbResourceFilterRequest(
    val titleFilter: String = "",
    val ownerId: ResourcesUserId = ResourcesUserId.NONE,
)