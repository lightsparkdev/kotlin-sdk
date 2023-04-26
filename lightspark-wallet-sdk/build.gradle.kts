import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.mgd.core.gradle.S3Upload
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinSerialization)
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.dokka.get().pluginId)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.gradleS3)
    alias(libs.plugins.mavenPublish)
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:${libs.versions.dokka.get()}")
    }
}

val VERSION = "1.0.0-SNAPSHOT"

kotlin {
    version = VERSION
    android {
        publishLibraryVariants("release")
    }

    val xcf = XCFramework()
    ios {
        binaries.framework {
            baseName = "shared"
            xcf.add(this)
        }
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
                implementation(libs.krypt)
                api(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.client.core)
                implementation(project(":lightspark-core"))
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
        val androidUnitTest by getting {
            dependsOn(androidMain)
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotest.assertions)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        val iosTest by getting
    }
}

buildkonfig {
    packageName = "com.lightspark.sdk.wallet"
    objectName = "LightsparkWalletConfig"

    defaultConfigs {
        buildConfigField(STRING, "VERSION", VERSION)
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf(
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
    )
}

kotlin {
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].kotlinOptions.freeCompilerArgs += "-Xexport-kdoc"
    }
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.S01)

    signAllPublications()
}

android {
    namespace = "com.lightspark.sdk.wallet"
    version = VERSION
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:${libs.versions.dokka.get()}")
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")
    }
}

tasks.dokkaHtml {
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        // Dokka's stylesheets and assets with conflicting names will be overriden.
        customStyleSheets = listOf(file("docs/overrides/logo-styles.css"))
        customAssets = listOf(file("docs/overrides/lightspark-logo-white.svg"))

        // Text used in the footer
        footerMessage = "(c) Lightspark Inc."
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
