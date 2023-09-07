plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    id("org.ysb33r.terraform") version "1.0.0"
}

group = "com.crowdproj.resources"
version = "0.1.0"

repositories {
    mavenCentral()
}

subprojects {
    this.group = rootProject.group
    this.version = rootProject.version

    repositories {
        mavenCentral()
    }
}

tasks {
    @Suppress("UNUSED_VARIABLE")
    val deploy: Task by creating {
        dependsOn("build")
    }
}