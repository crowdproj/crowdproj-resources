package ru.otus.otuskotlin.marketplace.stubs

import ru.otus.otuskotlin.marketplace.common.models.*

object ResourcesStubItem {
    val RES_DEMAND_ITEM1: Resources
        get() = Resources(
            id = ResourcesId("666"),
            resourcesId = OtherResourcesId("resource-1"),
            ownerId = ResourcesUserId("user-1"),
            visible = ResourcesVisible.VISIBLE_PUBLIC,
            permissionsClient = mutableSetOf(
                ResourcesPermissionClient.READ,
                ResourcesPermissionClient.UPDATE,
                ResourcesPermissionClient.DELETE,
                ResourcesPermissionClient.MAKE_VISIBLE_PUBLIC,
                ResourcesPermissionClient.MAKE_VISIBLE_GROUP,
                ResourcesPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
}