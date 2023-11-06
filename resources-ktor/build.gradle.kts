@file:Suppress("UNUSED_VARIABLE")

import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project

// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    id("application")
    kotlin("plugin.serialization")
    kotlin("multiplatform")
//    id("io.ktor.plugin")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

//application {
//    mainClass.set("io.ktor.server.cio.EngineMain")
//}
//
//ktor {
//    docker {
//        localImageName.set(project.name + "-ktor")
//        imageTag.set(project.version.toString())
//        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
//    }
//}
//
//jib {
//    container.mainClass = "io.ktor.server.cio.EngineMain"
//}

kotlin {
    jvm {
        withJava()
    }
    linuxX64 {}

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries {
            executable {
                entryPoint = "ru.otus.otuskotlin.marketplace.app.main"
@file:Suppress("UnstableApiUsage")

import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

plugins {
    kotlin("plugin.serialization")
    kotlin("multiplatform")
    id("io.ktor.plugin")
    id("com.bmuschko.docker-remote-api")
}

val ktorVersion: String by project
val serializationVersion: String by project
val datetimeVersion: String by project
val coroutinesVersion: String by project
val logbackVersion: String by project
val slf4jVersion: String by project
val uuidVersion: String by project

fun ktorServer(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-server-$module:$version"

fun ktorClient(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-client-$module:$version"

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

kotlin {
    jvm { withJava() }
    linuxX64 {
        binaries {
            executable {
                baseName = project.name
                entryPoint = "com.crowdproj.resources.app.main"
            }
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-serialization:$ktorVersion")

                implementation(ktorClient("core"))
                implementation(ktorClient("cio"))

                implementation(ktorServer("content-negotiation"))
                implementation(ktorServer("auto-head-response"))
                implementation(ktorServer("caching-headers"))
                implementation(ktorServer("cors"))
                implementation(ktorServer("call-id"))
                //implementation(ktorServer("websockets"))
                implementation(ktorServer("config-yaml"))
                implementation(ktorServer("core"))
                implementation(ktorServer("cio"))
                implementation(ktorServer("auth"))

                implementation("com.benasher44:uuid:$uuidVersion")

                implementation(project(":resources-log"))

                implementation(project(":resources-common"))

                implementation(project(":resources-api-v1"))
                implementation(project(":resources-api-v1-mappers"))
                implementation(project(":resources-biz"))
//                implementation(project(":crowdproj-ad-repo-inmemory"))
                //implementation(project(":crowdproj-ad-repo-stubs"))
                implementation(project(":resources-swagger"))

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"

                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                implementation(ktor("cio")) // "io.ktor:ktor-server-cio:$ktorVersion"
                implementation(ktor("auth")) // "io.ktor:ktor-server-auth:$ktorVersion"
                implementation(ktor("auto-head-response")) // "io.ktor:ktor-server-auto-head-response:$ktorVersion"
                implementation(ktor("caching-headers")) // "io.ktor:ktor-server-caching-headers:$ktorVersion"
                implementation(ktor("cors")) // "io.ktor:ktor-server-cors:$ktorVersion"
                implementation(ktor("websockets")) // "io.ktor:ktor-server-websockets:$ktorVersion"
                implementation(ktor("config-yaml")) // "io.ktor:ktor-server-config-yaml:$ktorVersion"
                implementation(ktor("content-negotiation")) // "io.ktor:ktor-server-content-negotiation:$ktorVersion"
                implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
                implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"

                implementation(project(":resources-common"))
                implementation(project(":resources-biz"))

                // v2 api
//                implementation(project(":ok-marketplace-api-v2-kmp"))
//                implementation(project(":ok-marketplace-mappers-v2"))

                // Stubs
                implementation(project(":resources-stubs"))

                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(ktor("test-host"))
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation(ktor("websockets", prefix = "client-"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(ktorServer("test-host"))
                implementation(ktorClient("content-negotiation"))
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                implementation(ktor("netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"

                // jackson
                implementation(ktor("jackson", "serialization")) // io.ktor:ktor-serialization-jackson
                implementation(ktor("content-negotiation")) // io.ktor:ktor-server-content-negotiation
                implementation(ktor("kotlinx-json", "serialization")) // io.ktor:ktor-serialization-kotlinx-json

                implementation(ktor("locations"))
                implementation(ktor("caching-headers"))
                implementation(ktor("call-logging"))
                implementation(ktor("auto-head-response"))
                implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
                implementation(ktor("default-headers")) // "io.ktor:ktor-cors:$ktorVersion"
                implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
                implementation(ktor("auto-head-response"))

                implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
                implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"
                implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                // transport models
                implementation(project(":resources-api-v1"))
                implementation(project(":resources-api-v1-mappers"))
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation("ch.qos.logback:logback-access:$logbackVersion")

                implementation("org.slf4j:slf4j-api:$slf4jVersion")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(ktor("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation(ktor("websockets", prefix = "client-"))
            }
        }

        val linuxX64Main by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }

        @Suppress("UnstableApiUsage") val linuxX64Test by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
ktor {
    docker {
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
        localImageName.set(project.name)
        imageTag.set("${project.version}")
        portMappings.set(
            listOf(
                io.ktor.plugin.features.DockerPortMapping(
                    80,
                    8080,
                    io.ktor.plugin.features.DockerPortMappingProtocol.TCP
                )
            )
        )

//        externalRegistry.set(
//            io.ktor.plugin.features.DockerImageRegistry.dockerHub(
//                appName = provider { "ktor-app" },
//                username = providers.environmentVariable("DOCKER_HUB_USERNAME"),
//                password = providers.environmentVariable("DOCKER_HUB_PASSWORD")
//            )
//        )
    }

}

tasks {
    val linkReleaseExecutableLinuxX64 by getting(KotlinNativeLink::class)
    val nativeFile = linkReleaseExecutableLinuxX64.binary.outputFile
//    val linkDebugExecutableLinuxX64 by getting(KotlinNativeLink::class)
//    val nativeFile = linkDebugExecutableLinuxX64.binary.outputFile
    val linuxX64ProcessResources by getting(ProcessResources::class)

    val dockerDockerfile by creating(Dockerfile::class) {
        dependsOn(linkReleaseExecutableLinuxX64)
        dependsOn(linuxX64ProcessResources)
        group = "docker"
        from("ubuntu:23.04")
        doFirst {
            copy {
                from(nativeFile)
                from(linuxX64ProcessResources.destinationDir)
                into("${this@creating.destDir.get()}")
            }
        }
        copyFile(nativeFile.name, "/app/")
        copyFile("application.yaml", "/app/")
        exposePort(8081)
        workingDir("/app")
        entryPoint("/app/${nativeFile.name}", "-config=./application.yaml")
    }
    val registryUser: String? = System.getenv("CONTAINER_REGISTRY_USER")
    val registryPass: String? = System.getenv("CONTAINER_REGISTRY_PASS")
    val registryHost: String? = System.getenv("CONTAINER_REGISTRY_HOST")
    val registryPref: String? = System.getenv("CONTAINER_REGISTRY_PREF")
    val imageName = registryPref?.let { "$it/${project.name}" } ?: project.name.toString()

    val dockerBuildNativeImage by creating(DockerBuildImage::class) {
        group = "docker"
        dependsOn(dockerDockerfile)
        images.add("$imageName:${project.version}")
        images.add("$imageName:latest")
    }
    val dockerPushNativeImage by creating(DockerPushImage::class) {
        group = "docker"
        dependsOn(dockerBuildNativeImage)
        images.set(dockerBuildNativeImage.images)
        registryCredentials {
            username.set(registryUser)
            password.set(registryPass)
            url.set("https://$registryHost/v1/")
        }
    }

    create("deploy") {
        group = "build"
        dependsOn(dockerPushNativeImage)
    }
}
