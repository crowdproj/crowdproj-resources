package com.crowdproj.resources.app.plugins

import com.crowdproj.resources.common.repo.IResourceRepository
import io.ktor.server.application.*

actual fun Application.getDatabaseConf(type: ResDbType): IResourceRepository {
    TODO("Not yet implemented")
}