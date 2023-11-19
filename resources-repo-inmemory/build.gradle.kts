plugins {
    kotlin("multiplatform")
}

version = rootProject.version

kotlin {
    jvm {}
    linuxX64 {}

    sourceSets {
        val cache4kVersion: String by project
        val coroutinesVersion: String by project
        val uuidVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(project(":resources-common"))

                implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("com.benasher44:uuid:$uuidVersion")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":resources-repo-tests"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
