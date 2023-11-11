package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.repo.*
class ResourceRepositoryMock(
    private val invokeCreateResource: (DbResourceRequest) -> DbResourceResponse = { DbResourceResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadResource: (DbResourceIdRequest) -> DbResourceResponse = { DbResourceResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateResource: (DbResourceRequest) -> DbResourceResponse = { DbResourceResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteResource: (DbResourceIdRequest) -> DbResourceResponse = { DbResourceResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchResource: (DbResourceFilterRequest) -> DbResourcesResponse = { DbResourcesResponse.MOCK_SUCCESS_EMPTY },
): IResourceRepository {
    override suspend fun createResource(rq: DbResourceRequest): DbResourceResponse {
        return invokeCreateResource(rq)
    }

    override suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse {
        return invokeReadResource(rq)
    }

    override suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse {
        return invokeUpdateResource(rq)
    }

    override suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse {
        return invokeDeleteResource(rq)
    }

    override suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse {
        return invokeSearchResource(rq)
    }
}