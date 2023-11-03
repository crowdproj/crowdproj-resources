rootProject.name = "crowdproj-resources"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings
    val ktorVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("io.ktor.plugin") version ktorVersion apply false

        id("io.kotest.multiplatform") version kotestVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }
}


//include("specs")
include("resources-api-v1")
include("resources-api-v2")
include("resources-common")
include("resources-api-v1-mappers")
include("resources-api-v2-mappers")
include("resources-biz")
include("resources-stubs")
include("resources-ktor")
include("resources-kafka")
include("resources-lib-cor")
include("resources-lib-logging-common")
include("resources-lib-logging-logback")
include("resources-app-common")
include("resources-api-log1")
include("resources-mappers-log1")