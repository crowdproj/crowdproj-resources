package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.models.Resources
import com.crowdproj.resources.common.models.ResourcesError

data class DbResourcesResponse(
    override val data: List<Resources>?,
    override val isSuccess: Boolean,
    override val errors: List<ResourcesError> = emptyList(),
): IDbResponse<List<Resources>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbResourcesResponse(emptyList(), true)
        fun success(result: List<Resources>) = DbResourcesResponse(result, true)
        fun error(errors: List<ResourcesError>) = DbResourcesResponse(null, false, errors)
        fun error(error: ResourcesError) = DbResourcesResponse(null, false, listOf(error))
    }
}