package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.models.ResourcesError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<ResourcesError>
}