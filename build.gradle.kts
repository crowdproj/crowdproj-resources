import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") apply false
}

group = "com.crowdproj.resources"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    this.group = rootProject.group
    this.version = rootProject.version

    tasks.withType<KotlinCompile> {
        val jvmTarget: String by project
        kotlinOptions.jvmTarget = jvmTarget
    }
}

tasks.wrapper {
    gradleVersion = "8.3"
    // You can either download the binary-only version of Gradle (BIN) or
    // the full version (with sources and documentation) of Gradle (ALL)
    distributionType = Wrapper.DistributionType.ALL
}