package com.crowdproj.resources.common

import com.crowdproj.resources.common.config.ResourcesCorSettings
import kotlinx.datetime.Instant
import com.crowdproj.resources.common.models.*
import com.crowdproj.resources.common.repo.IResourceRepository
import com.crowdproj.resources.common.stubs.ResourcesStubs

data class ResourcesContext(
    var command: ResourcesCommand = ResourcesCommand.NONE,
    var state: ResourcesState = ResourcesState.NONE,
    val errors: MutableList<ResourcesError> = mutableListOf(),
    var timeStart: Instant = Instant.NONE,
    var settings: ResourcesCorSettings = ResourcesCorSettings.NONE,
    var workMode: ResourcesWorkMode = ResourcesWorkMode.PROD,
    var stubCase: ResourcesStubs = ResourcesStubs.NONE,
    var requestId: ResourcesRequestId = ResourcesRequestId.NONE,
    var resourceRepo: IResourceRepository = IResourceRepository.NONE,

    var resourceRequest: Resources = Resources(),
    var resourceFilterRequest: ResourcesFilter = ResourcesFilter(),

    var resourceValidating: Resources = Resources(),
    var resourceFilterValidating: ResourcesFilter = ResourcesFilter(),

    var resourceValidated: Resources = Resources(),
    var resourceFilterValidated: ResourcesFilter = ResourcesFilter(),

    var resourceRepoRead: Resources = Resources(),
    var resourceRepoPrepare: Resources = Resources(),
    var resourceRepoDone: Resources = Resources(),
    var resourcesRepoDone: MutableList<Resources> = mutableListOf(),

    var resourceResponse: Resources = Resources(),
    var resourcesResponse: MutableList<Resources> = mutableListOf(),
)