
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import java.net.URL

buildscript {
    dependencies {
        classpath(libs.gradleClasspath.android)
        classpath(libs.gradleClasspath.dokka)
        classpath(libs.gradleClasspath.ktlint)
        classpath(libs.gradleClasspath.kotlin)
        classpath(libs.gradleClasspath.mavenPublish)
        classpath(libs.gradleClasspath.downloadFile)
        // Needed for https://github.com/google/dagger/issues/3068.
        classpath("com.squareup:javapoet:1.13.0")
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

apply(plugin = "org.jetbrains.dokka")
apply(plugin = "org.jlleitschuh.gradle.ktlint")

tasks.create<Exec>("hasCoreChanged") {
    group = "release"
    description = "Checks if the core module has changed since the last release. Fails if so."
    commandLine("./scripts/check_core_version.sh")
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        outputToConsole.set(true)
        verbose.set(true)
        disabledRules.set(listOf("no-wildcard-imports"))
        filter {
            exclude("**/buildkonfig/**")
        }
    }

    tasks.create<Exec>("bumpAndTagVersion") {
        group = "release"
        description = "Tags the current version in git."
        val cmd = mutableListOf("../scripts/versions.main.kts", "-f", "-t")
        if (project.hasProperty("newVersion")) {
            cmd.add(project.properties["newVersion"].toString())
        }
        commandLine(*cmd.toTypedArray())
    }

    tasks.create<Exec>("bumpVersion") {
        group = "release"
        description = "Tags the current version in git."
        val cmd = mutableListOf("../scripts/versions.main.kts", "-f")
        if (project.hasProperty("newVersion")) {
            cmd.addAll(listOf("-v", project.properties["newVersion"].toString()))
        }
        commandLine(*cmd.toTypedArray())
    }

    tasks.withType<DokkaTaskPartial>().configureEach {
        dokkaSourceSets.configureEach {
            reportUndocumented.set(false)
            skipDeprecated.set(true)
            jdkVersion.set(8)
            if (project.file("README.md").exists()) {
                includes.from(project.file("README.md"))
            }
            externalDocumentationLink {
                url.set(URL("https://app.lightspark.com/docs/reference/kotlin"))
                packageListUrl.set(URL("https://app.lightspark.com/docs/reference/kotlin/package-list"))
            }
        }
    }

    plugins.withId("com.vanniktech.maven.publish.base") {
        configure<MavenPublishBaseExtension> {
            publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
            signAllPublications()
            pom {
                name.set(project.name)
                packaging = "aar"
                url.set("https://github.com/lightsparkdev/kotlin-sdk")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/lightsparkdev/kotlin-sdk.git")
                    developerConnection.set("scm:git:ssh://git@github.com/lightsparkdev/kotlin-sdk.git")
                    url.set("https://github.com/lightsparkdev/kotlin-sdk")
                }
                developers {
                    developer {
                        name.set("Lightspark Group, Inc.")
                        id.set("lightsparkdev")
                        url.set("https://github.com/lightsparkdev")
                    }
                }
            }
        }
    }

    // Workaround for https://github.com/Kotlin/dokka/issues/2977.
    // We disable the C Interop IDE metadata task when generating documentation using Dokka.
    gradle.taskGraph.whenReady {
        val hasDokkaTasks = gradle.taskGraph.allTasks.any {
            it is org.jetbrains.dokka.gradle.AbstractDokkaTask
        }
        if (hasDokkaTasks) {
            tasks.matching {
                "CInteropMetadataDependencyTransformationTask" in (it::class.qualifiedName ?: "")
            }.configureEach {
                enabled = false
            }
        }
    }
}

tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule") {
    moduleName.set("Lightspark Kotlin+Java SDKs")
    pluginsMapConfiguration.set(
        mapOf(
            "org.jetbrains.dokka.base.DokkaBase" to """
          {
            "customStyleSheets": [
              "${rootDir.resolve("docs/css/logo-styles.css")}"
            ],
            "customAssets" : [
              "${rootDir.resolve("docs/images/lightspark-logo-white.svg")}"
            ]
          }
            """.trimIndent(),
        ),
    )
}
