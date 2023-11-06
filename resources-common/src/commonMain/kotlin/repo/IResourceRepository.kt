package com.crowdproj.resources.common.repo

interface IResourceRepository {
    suspend fun createResource(rq: DbResourceRequest): DbResourceResponse
    suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse
    suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse
    suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse
    suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse
    companion object {
        val NONE = object : IResourceRepository {
            inner class NoneRepository(): RuntimeException("Repository is not set")
            suspend fun forbidden(): Nothing = throw NoneRepository()
            override suspend fun createResource(rq: DbResourceRequest): DbResourceResponse {
                forbidden()
            }

            override suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse {
                forbidden()
            }

            override suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse {
                forbidden()
            }

            override suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse {
                forbidden()
            }

            override suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse {
                forbidden()
            }
        }
    }
}
