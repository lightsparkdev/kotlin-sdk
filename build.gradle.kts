tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

buildscript {
    dependencies {
        classpath(libs.gradleClasspath.android)
        classpath(libs.gradleClasspath.dokka)
        classpath(libs.gradleClasspath.ktlint)
        classpath(libs.gradleClasspath.kotlin)
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
}
