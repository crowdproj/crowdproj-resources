plugins {
    kotlin("multiplatform")
}

version = rootProject.version

kotlin {
    jvm {}
    linuxX64 {}

    sourceSets {
        val coroutinesVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(project(":resources-common"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
                api(kotlin("test-junit"))
            }
        }
    }
}