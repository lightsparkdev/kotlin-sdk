import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.dokka.get().pluginId)
    alias(libs.plugins.buildKonfig)
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
    android {
        publishLibraryVariants("release")
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        // TODO: Re-enable when https://github.com/ACINQ/secp256k1-kmp/pull/79 is merged.
        // iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "crypto"
            xcf.add(this)
        }
    }

//    js(IR) {
//        browser {
//            binaries.executable()
//        }
//
//        nodejs {
//            binaries.executable()
//        }
//    }

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
                implementation(libs.acinq.bitcoin)
                implementation(libs.acinq.secp256k1)
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
            }
        }
        val jvmMain by getting {
            dependsOn(commonJvmAndroidMain)
            dependencies {
                implementation(libs.kotlinx.coroutines.jdk8)
                implementation("fr.acinq.secp256k1:secp256k1-kmp-jni-jvm:0.10.0")
            }
        }

        val androidMain by getting {
            dependsOn(commonJvmAndroidMain)
            dependencies {
                implementation("fr.acinq.secp256k1:secp256k1-kmp-jni-android:0.10.0")
            }
        }
        val androidUnitTest by getting
    }
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
