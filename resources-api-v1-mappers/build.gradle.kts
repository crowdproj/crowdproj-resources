plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":resources-api-v1"))
    implementation(project(":resources-common"))

    testImplementation(kotlin("test-junit"))
}