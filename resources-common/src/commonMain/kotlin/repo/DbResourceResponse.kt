package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.helpers.errorRepoConcurrency
import com.crowdproj.resources.common.models.Resources
import com.crowdproj.resources.common.models.ResourcesError
import com.crowdproj.resources.common.models.ResourcesLock
import com.crowdproj.resources.common.helpers.errorNotFound as mkplErrorNotFound
import com.crowdproj.resources.common.helpers.errorNotFound as mkplErrorEmptyId

data class DbResourceResponse(
    override val data: Resources?,
    override val isSuccess: Boolean,
    override val errors: List<ResourcesError> = emptyList()
): IDbResponse<Resources> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbResourceResponse(null, true)
        fun success(result: Resources) = DbResourceResponse(result, true)
        fun error(errors: List<ResourcesError>, data: Resources? = null) = DbResourceResponse(data, false, errors)
        fun error(error: ResourcesError, data: Resources? = null) = DbResourceResponse(data, false, listOf(error))

        val errorNotFound = error(mkplErrorNotFound)
        val errorEmptyId = error(mkplErrorEmptyId)
        fun errorConcurrent(lock: ResourcesLock, rs: Resources?) = error(
            errorRepoConcurrency(lock, rs?.lock?.let { ResourcesLock(it.asString()) }),
            rs
        )
    }
}