import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.mgd.core.gradle.S3Upload

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinSerialization)
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.dokka.get().pluginId)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.gradleS3)
    id(libs.plugins.mavenPublish.get().pluginId)
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:${libs.versions.dokka.get()}")
    }
}

val VERSION_NAME: String by project

kotlin {
    version = VERSION_NAME
    androidTarget {
        publishLibraryVariants("release")
    }

    jvm {
        // This doesn't work, unfortunately.. https://youtrack.jetbrains.com/issue/KT-30878
        // withJava()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kase64)
                implementation(libs.kotlin.serialization.json)
                api(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.encoding)
                // implementation(project(":crypto"))
                implementation(libs.lightspark.crypto)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotest.assertions)
            }
        }
        val commonJvmAndroidMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.kotlinx.coroutines.jdk8)
                implementation(libs.ktor.client.okhttp)
            }
        }
        val jvmMain by getting {
            dependsOn(commonJvmAndroidMain)
            dependencies {
                implementation(libs.kotlinx.coroutines.jdk8)
                implementation(libs.ktor.client.okhttp)
            }
        }

        val androidMain by getting {
            dependsOn(commonJvmAndroidMain)
            dependencies {
                implementation(libs.appauth)
                // If you want to use DataStoreAuthStateStorage, you need to add the following implementation dependency to your build.gradle.kts.
                compileOnly(libs.androidx.datastore.preferences)
            }
        }
        val androidUnitTest by getting
    }
}

buildkonfig {
    packageName = "com.lightspark.sdk.core"
    objectName = "LightsparkCoreConfig"

    defaultConfigs {
        buildConfigField(STRING, "VERSION", VERSION_NAME)
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf(
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
    )
}

kotlin {
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].kotlinOptions.freeCompilerArgs += "-Xexport-kdoc"
    }
}

mavenPublishing {
}

android {
    namespace = "com.lightspark.sdk.core"
    version = VERSION_NAME
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    dependencies {
        dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:${libs.versions.dokka.get()}")
        coreLibraryDesugaring(libs.desugar.jdk.libs)
    }
}

tasks.register<Copy>("generateSdkDocs") {
    group = "documentation"
    dependsOn("dokkaGfm")
    dependsOn("dokkaHtml")
    from("build/dokka")
    into("docs")
}

s3 {
    bucket = "lsdev.web-dev"
    region = "us-west-2"
}

tasks.register<S3Upload>("uploadDocsToS3") {
    group = "documentation"
    dependsOn("generateSdkDocs")
    bucket = "ldev.web-dev"
    keyPrefix = "docs/kotlin"
    sourceDir = "docs/html"
}
