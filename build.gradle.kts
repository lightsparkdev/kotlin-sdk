buildscript {
    val compose_version by extra("1.6.1")
}
plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version("7.3.1").apply(false)
    kotlin("multiplatform").version("1.7.20").apply(false)
    kotlin("plugin.serialization") version "1.7.20" apply false
    id("com.android.application") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("com.codingfeline.buildkonfig") version "0.13.3" apply false
    id("com.apollographql.apollo3") version "3.7.3" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        // Needed for krypt.
        maven { url = uri("https://repo.repsy.io/mvn/chrynan/public") }
    }
}
