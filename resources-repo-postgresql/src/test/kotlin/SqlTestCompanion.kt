package com.crowdproj.resources.repo.sql

import com.benasher44.uuid.uuid4
import org.testcontainers.containers.PostgreSQLContainer
import com.crowdproj.resources.common.models.Resources
import java.time.Duration

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "postgres"
    private const val SCHEMA = "postgres"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        test: String,
        initObjects: Collection<Resources> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): RepoResourceSQL {
        return RepoResourceSQL(
            SqlProperties(
                url = url,
                user = USER,
                password = PASS,
                schema = SCHEMA,
                table = "resource-$test",
            ),
            initObjects,
            randomUuid = randomUuid
        )
    }
}
