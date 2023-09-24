rootProject.name = "crowdproj-resources"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val ktorPluginVersion: String by settings
        val codeGeneratorVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorPluginVersion apply false

        id("com.crowdproj.generator") version codeGeneratorVersion apply false

        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

//include("specs")
include("resources-common")
include("resources-api-v1")
include("resources-api-v1-mappers")
include("resources-stubs")
include("resources-biz")
include("resources-ktor")
include("resources-log")
include("resources-swagger")
