plugins {
    kotlin("multiplatform")
}

version = rootProject.version

kotlin {
    jvm {}
    linuxX64 {}

    sourceSets {
        val datetimeVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                api(project(":resources-lib-logging-common"))

                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
                api(project(":resources-lib-logging-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}