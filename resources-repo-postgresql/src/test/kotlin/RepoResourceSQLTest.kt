package com.crowdproj.resources.repo.sql

import com.crowdproj.resources.repo.tests.*
import com.crowdproj.resources.common.repo.IResourceRepository
import kotlin.random.Random

val random = Random(System.currentTimeMillis())
class RepoAdSQLCreateTest : RepoResourceCreateTest() {
    override val repo: IResourceRepository = SqlTestCompanion.repoUnderTestContainer(
        "create-" + random.nextInt(),
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoAdSQLDeleteTest : RepoResourceDeleteTest() {
    override val repo: IResourceRepository = SqlTestCompanion.repoUnderTestContainer(
        "delete_" + random.nextInt(),
        initObjects
    )
}

class RepoAdSQLReadTest : RepoResourceReadTest() {
    override val repo: IResourceRepository = SqlTestCompanion.repoUnderTestContainer(
        "read" + random.nextInt(),
        initObjects
    )
}

class RepoAdSQLSearchTest : RepoResourceSearchTest() {
    override val repo: IResourceRepository = SqlTestCompanion.repoUnderTestContainer(
        "search" + random.nextInt(),
        initObjects
    )
}

class RepoAdSQLUpdateTest : RepoResourceUpdateTest() {
    override val repo: IResourceRepository = SqlTestCompanion.repoUnderTestContainer(
        "update" + random.nextInt(),
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}