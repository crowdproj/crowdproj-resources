package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.models.Resources
import com.crowdproj.resources.common.models.ResourcesId
import com.crowdproj.resources.common.models.ResourcesLock

data class DbResourceIdRequest(
    val id: ResourcesId,
    val lock: ResourcesLock = ResourcesLock.NONE,
) {
    constructor(ad: Resources): this(ad.id, ad.lock)
}