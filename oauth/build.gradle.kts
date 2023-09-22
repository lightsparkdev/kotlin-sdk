@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("android")
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.dokka.get().pluginId)
    id(libs.plugins.mavenPublish.get().pluginId)
}

android {
    namespace = "com.lightspark.sdk.auth.oauth"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    dependencies {
        dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:${libs.versions.dokka.get()}")
        coreLibraryDesugaring(libs.desugar.jdk.libs)
    }
    kotlin {
        jvmToolchain(11)
    }
}

dependencies {
    implementation(libs.appauth)
    // If you want to use DataStoreAuthStateStorage, you need to add the following implementation dependency to your build.gradle.kts.
    compileOnly(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.ktor.client.core)
    // Can use this while locally developing, but should use the published version when publishing:
    // implementation(project(":core"))
    implementation(libs.lightspark.core)

    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertions)
}

tasks.matching { name == "bumpAndTagVersion" || name == "bumpVersion" }.configureEach {
    doFirst {
        if (project.configurations["commonMainImplementationDependenciesMetadata"].resolvedConfiguration
            .lenientConfiguration.artifacts
            .any { it.moduleVersion.id.group == "Lightspark" && it.moduleVersion.id.name == "core" }
        ) {
            throw GradleException("Cannot depend directly on core. Depend on the published module instead.")
        }
    }
    dependsOn(":hasCoreChanged")
}
