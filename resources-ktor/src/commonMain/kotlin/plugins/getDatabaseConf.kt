package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.common.repo.IResourceRepository
import io.ktor.server.application.*

expect fun Application.getDatabaseConf(type: ResDbType): IResourceRepository

enum class ResDbType(val confName: String) {
    PROD("prod"), TEST("test")
}