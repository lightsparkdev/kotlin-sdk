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
    alias(libs.plugins.gradleS3)
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:${libs.versions.dokka.get()}")
    }
}

kotlin {
    android()

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
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
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotest.assertions)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        val androidMain by getting {
            dependsOn(jvmMain)
            dependencies {
                implementation(libs.appauth)
                // If you want to use DataStoreAuthStateStorage, you need to add the following implementation dependency to your build.gradle.kts.
                compileOnly(libs.androidx.datastore.preferences)
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
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

android {
    namespace = "com.lightspark.sdk"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:${libs.versions.dokka.get()}")
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.1")
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
