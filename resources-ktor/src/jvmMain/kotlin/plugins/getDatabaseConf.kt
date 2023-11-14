package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.common.repo.IResourceRepository
import com.crowdproj.resources.configs.ConfigPaths
import com.crowdproj.resources.repo.inmemory.ResourcesRepoInMemory
import io.ktor.server.application.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

actual fun Application.getDatabaseConf(type: ResDbType): IResourceRepository {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: 'inmemory'"
        )
    }
}


private fun Application.initInMemory(): IResourceRepository {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return ResourcesRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}