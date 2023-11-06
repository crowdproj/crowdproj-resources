package com.crowdproj.resources.common.models

enum class ResourcesCommand(val isUpdatable: Boolean) {
    NONE(isUpdatable = false),
    CREATE(isUpdatable = true),
    READ(isUpdatable = false),
    UPDATE(isUpdatable = true),
    DELETE(isUpdatable = true),
    SEARCH(isUpdatable = false),
    ;
}