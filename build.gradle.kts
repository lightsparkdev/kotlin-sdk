import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

buildscript {
    dependencies {
        classpath(libs.gradleClasspath.android)
        classpath(libs.gradleClasspath.dokka)
        classpath(libs.gradleClasspath.ktlint)
        classpath(libs.gradleClasspath.kotlin)
        classpath(libs.gradleClasspath.mavenPublish)
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

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        outputToConsole.set(true)
        verbose.set(true)
        disabledRules.set(listOf("no-wildcard-imports"))
    }

    tasks.create<Exec>("bumpAndTagVersion") {
        group = "release"
        description = "Tags the current version in git."
        commandLine("../scripts/versions.main.kts", "-f")
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
}
