import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import de.undercouch.gradle.tasks.download.Download

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.dokka.get().pluginId)
    alias(libs.plugins.buildKonfig)
    id(libs.plugins.mavenPublish.get().pluginId)
    id(libs.plugins.downloadFile.get().pluginId)
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:${libs.versions.dokka.get()}")
    }
}

val VERSION_NAME: String by project

kotlin {
    version = VERSION_NAME
    android {
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
                implementation(libs.krypt)
                api(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotest.assertions)
                implementation(libs.acinq.secp256k1)
                implementation(libs.acinq.secp256k1.jni.jvm)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.jna)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("net.java.dev.jna:jna:${libs.jna.get().version}@aar")
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotest.assertions)
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotest.assertions)
                implementation("androidx.test:runner:1.5.2")
                implementation("androidx.test.ext:junit:1.1.5")
                implementation("androidx.test.ext:junit-ktx:1.1.5")
                implementation(libs.acinq.secp256k1)
                implementation(libs.acinq.secp256k1.jni.android)
            }
        }
    }
}

val CRYPTO_UNIFFI_VERSION = "0.0.1"
tasks.create<Download>("downloadUniffiBinaries") {
    // TODO(Jeremy): Replace this with the actual artifact url(s) once it's published.
    src("https://github.com/lightsparkdev/lightspark-crypto-uniffi/archive/refs/tags/@lightsparkdev/lightspark-crypto-uniffi@$CRYPTO_UNIFFI_VERSION.tar.gz")
    dest(project.buildDir)
    onlyIfModified(true)
}

buildkonfig {
    packageName = "com.lightspark.sdk.crypto"
    objectName = "LightsparkCryptoConfig"

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
    namespace = "com.lightspark.sdk.crypto"
    version = VERSION_NAME
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            jniLibs.srcDir("src/androidMain/jniLibs")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    dependencies {
        dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:${libs.versions.dokka.get()}")
        coreLibraryDesugaring(libs.desugar.jdk.libs)
    }
}
