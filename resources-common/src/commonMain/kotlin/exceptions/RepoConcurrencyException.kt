package com.crowdproj.resources.common.exceptions

import com.crowdproj.resources.common.models.ResourcesLock

class RepoConcurrencyException(expectedLock: ResourcesLock, actualLock: ResourcesLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)