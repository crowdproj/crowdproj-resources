plugins {
    kotlin("multiplatform")
}

version = rootProject.version

kotlin {
    jvm {}
    linuxX64 {}

    sourceSets {
        val coroutinesVersion: String by project
        val corVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation("com.crowdproj:kotlin-cor:$corVersion")

                implementation(project(":resources-common"))
                implementation(project(":resources-stubs"))
                implementation(project(":resources-auth"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(project(":resources-repo-stubs"))
                implementation(project(":resources-repo-tests"))
                implementation(project(":resources-repo-inmemory"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
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
