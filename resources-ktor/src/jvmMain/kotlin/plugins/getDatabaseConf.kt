package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.common.repo.IResourceRepository
import com.crowdproj.resources.app.configs.ConfigPaths
import com.crowdproj.resources.app.configs.PostgresConfig
import com.crowdproj.resources.repo.inmemory.ResourcesRepoInMemory
import com.crowdproj.resources.repo.sql.RepoResourceSQL
import com.crowdproj.resources.repo.sql.SqlProperties
import io.ktor.server.application.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

actual fun Application.getDatabaseConf(type: ResDbType): IResourceRepository {

    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: 'inmemory' or 'psql'"
        )
    }
}

private fun Application.initPostgres(): IResourceRepository {
    val config = PostgresConfig(environment.config)
    return RepoResourceSQL(
        properties = SqlProperties(
            url = config.url,
            user = config.user,
            password = config.password,
            schema = config.schema,

        )
    )
}

private fun Application.initInMemory(): IResourceRepository {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return ResourcesRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}