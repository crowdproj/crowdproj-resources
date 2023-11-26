import io.ktor.plugin.features.*
import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val serializationVersion: String by project
val logbackVersion: String by project
val jvmTarget: String by project

fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    id("application")
    id("io.ktor.plugin")
    kotlin("plugin.serialization")
    kotlin("multiplatform")
}
val webjars: Configuration by configurations.creating
dependencies {
    val swaggerUiVersion: String by project
    webjars("org.webjars:swagger-ui:$swaggerUiVersion")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

jib {
    container { mainClass = application.mainClass.get() }
}

ktor {
    docker {
        localImageName.set(project.name)
        imageTag.set(project.version.toString())
        jreVersion.set(JreVersion.valueOf("JRE_$jvmTarget"))
    }
}

kotlin {
    jvm {}
    linuxX64 {
        binaries {
            executable {
                entryPoint = "com.crowdproj.resources.app.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                implementation(ktor("cio"))
                implementation(ktor("config-yaml"))
                implementation(ktor("websockets"))

                implementation(ktor("content-negotiation"))
                implementation(ktor("cors"))
                implementation(ktor("caching-headers"))
                implementation(ktor("auto-head-response"))
                implementation(ktor("kotlinx-json", prefix = "serialization-"))
                implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"

                implementation(project(":resources-common"))
                implementation(project(":resources-api-log"))
                implementation(project(":resources-api-log-mappers"))
                implementation(project(":resources-api-v1"))
                implementation(project(":resources-api-v1-mappers"))
                implementation(project(":resources-stubs"))
                implementation(project(":resources-biz"))
                implementation(project(":resources-repo-postgresql"))
                implementation(project(":resources-repo-inmemory"))

                implementation(project(":resources-repo-stubs"))
                implementation(project(":resources-swagger"))

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(ktor("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation(ktor("websockets", prefix = "client-"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(ktor("call-logging"))
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation(project(":resources-lib-logging-logback"))
                implementation(ktor("default-headers"))
            }
        }
    }
    tasks {
        @Suppress("UnstableApiUsage")
        withType<ProcessResources>().configureEach {
            println("RESOURCES: ${this.name} ${this::class}")
            from("$rootDir/resources-api-v1/spec-resources-v1.yaml") {
                into("specs")
                filter {
                    // Устанавливаем версию в сваггере
                    it.replace("\${VERSION_APP}", project.version.toString())
                }
            }
            from("$rootDir/resources-api-v1/build/base.yaml") {
                into("specs")
            }
            webjars.forEach { jar ->
                val conf = webjars.resolvedConfiguration
                println("JarAbsPa: ${jar.absolutePath}")
                val artifact = conf.resolvedArtifacts.find { it.file.toString() == jar.absolutePath } ?: return@forEach
                val upStreamVersion = artifact.moduleVersion.id.version.replace("(-[\\d.-]+)", "")
                copy {
                    from(zipTree(jar))
                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    into(file("${buildDir}/webjars-content/${artifact.name}"))
                }
                with(this@configureEach) {
                    this.duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    from(
                        "${buildDir}/webjars-content/${artifact.name}/META-INF/resources/webjars/${artifact.name}/${upStreamVersion}"
                    ) { into(artifact.name) }
                    from(
                        "${buildDir}/webjars-content/${artifact.name}/META-INF/resources/webjars/${artifact.name}/${artifact.moduleVersion.id.version}"
                    ) { into(artifact.name) }
                }
            }
        }
    }
}
