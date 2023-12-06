import java.io.FileInputStream
import java.util.*
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.androidApplication.get().pluginId)
    kotlin("android")
    kotlin("kapt")
    alias(libs.plugins.daggerHiltAndroidGradle)
}

val versionPropertiesFile = project.file("version.properties")
val versionProperties = Properties()
try {
    versionProperties.load(FileInputStream(versionPropertiesFile))
} catch (e: Exception) {
    throw RuntimeException("Unable to load version.properties", e)
}

val jwtServerUrl: String = gradleLocalProperties(rootDir).getProperty("jwtServerUrl")

android {
    namespace = "com.lightspark.androidwalletdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lightspark.androidwalletdemo"
        minSdk = 24
        targetSdk = 34
        versionCode = versionProperties.getProperty("version_code").toInt()
        versionName = versionProperties.getProperty("version_name")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        manifestPlaceholders["appAuthRedirectScheme"] = "com.lightspark.androidwalletdemo"
        buildConfigField("String", "JWT_SERVER_URL", "\"${jwtServerUrl}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.zxing.core)
    implementation(libs.bundles.accompanist)

    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.mlkit.vision)
    implementation(libs.mlkit.barcode.scanning)
    implementation(libs.lightspark.composeqrcode)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(project(":wallet-sdk"))
    implementation(project(":core"))

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}
