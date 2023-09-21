package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.models.Resources
import com.crowdproj.resources.common.models.ResourcesError

data class DbResourceResponse(
    override val data: Resources?,
    override val isSuccess: Boolean,
    override val errors: List<ResourcesError> = emptyList()
): IDbResponse<Resources> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbResourceResponse(null, true)
        fun success(result: Resources) = DbResourceResponse(result, true)
        fun error(errors: List<ResourcesError>) = DbResourceResponse(null, false, errors)
        fun error(error: ResourcesError) = DbResourceResponse(null, false, listOf(error))
    }
}