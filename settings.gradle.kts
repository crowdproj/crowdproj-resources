rootProject.name = "crowdproj-resources"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("io.kotest.multiplatform") version kotestVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }
}
val runTests: String by settings
val shouldTest = runTests.toBoolean()

include("specs")
include("resources-api-v1")
include("resources-common")
include("resources-api-v1-mappers")
