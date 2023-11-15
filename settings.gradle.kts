rootProject.name = "crowdproj-resources"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings
    val ktorVersion: String by settings

    plugins {
        val codeGeneratorVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("io.ktor.plugin") version ktorVersion apply false
        id("io.kotest.multiplatform") version kotestVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("com.crowdproj.generator") version codeGeneratorVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

//include("specs")
include("resources-common")
include("resources-api-log")
include("resources-api-log-mappers")
include("resources-api-v1")
include("resources-api-v1-mappers")
include("resources-biz")
include("resources-stubs")
include("resources-repo-stubs")
include("resources-lib-log")
include("resources-ktor")
include("resources-repo-tests")
include("resources-repo-inmemory")
include("resources-swagger")
include("resources-repo-postgresql")
include("resources-lib-logging-common")
include("resources-lib-logging-logback")
