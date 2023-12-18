package com.crowdproj.resources.auth

import com.crowdproj.resources.common.models.Resources
import com.crowdproj.resources.common.models.ResourcesId
import com.crowdproj.resources.common.permissions.ResourcesPrincipalModel
import com.crowdproj.resources.common.permissions.ResourcesPrincipalRelations

fun Resources.resolveRelationsTo(principal: ResourcesPrincipalModel): Set<ResourcesPrincipalRelations> = setOfNotNull(
    ResourcesPrincipalRelations.NONE,
    ResourcesPrincipalRelations.NEW.takeIf { id == ResourcesId.NONE },
    ResourcesPrincipalRelations.OWN.takeIf { principal.id == ownerId },
    ResourcesPrincipalRelations.PUBLIC.takeIf { principal.id != ownerId },
)