package com.crowdproj.resources.common.models

data class ResourcesError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)