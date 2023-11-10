package ru.otus.otuskotlin.marketplace.backend.repository.inmemory

import com.crowdproj.resources.common.repo.*
import com.crowdproj.resources.stubs.CpwResourceStub

class ResourceRepoStub() : IResourceRepository {
    override suspend fun createResource(rq: DbResourceRequest): DbResourceResponse {
        return DbResourceResponse(
            data = CpwResourceStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse {
        return DbResourceResponse(
            data = CpwResourceStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse {
        return DbResourceResponse(
            data = CpwResourceStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse {
        return DbResourceResponse(
            data = CpwResourceStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse {
        return DbResourcesResponse(
            data = CpwResourceStub.prepareSearchList(id = ""),
            isSuccess = true,
        )
    }
}
