package ru.otus.otuskotlin.marketplace.common.models

data class ResourcesFilter(
    var searchString: String = "",
    var ownerId: ResourcesUserId = ResourcesUserId.NONE,
)