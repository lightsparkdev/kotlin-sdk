import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.apollographql.apollo3")
    id("com.codingfeline.buildkonfig")
}

object Versions {
    val apollo = "3.7.3"
    val kase64 = "1.0.6"
}

kotlin {
    android()
    
    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            xcf.add(this)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.apollographql.apollo3:apollo-runtime:${Versions.apollo}")
                implementation("com.apollographql.apollo3:apollo-normalized-cache:${Versions.apollo}")
                implementation("de.peilicke.sascha:kase64:${Versions.kase64}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
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

apollo {
    service("sparkcore") {
        packageName.set("com.lightspark.api")
        generateKotlinModels.set(true)
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf(
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    )
}

buildkonfig {
    packageName = "com.lightspark.conf"
    // objectName = "YourAwesomeConfig"
    // exposeObjectWithName = "YourAwesomePublicConfig"

    val token_id: String by project
    val token_secret: String by project
    val lightspark_endpoint: String by project
    val bitcoin_network: String by project
    defaultConfigs {
        buildConfigField(STRING, "LIGHTSPARK_TOKEN_ID", token_id)
        buildConfigField(STRING, "LIGHTSPARK_TOKEN", token_secret)
        buildConfigField(STRING, "LIGHTSPARK_ENDPOINT", lightspark_endpoint)
        buildConfigField(STRING, "BITCOIN_NETWORK", bitcoin_network)
    }
}

android {
    namespace = "com.lightspark.sdk"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}

tasks.register<Exec>("updateGraphQLSchema") {
    workingDir = File(projectDir, "src/commonMain/graphql")
    commandLine = listOf(
        "git clone --no-checkout --depth 1 git@github.com:lightsparkdev/webdev.git &&",
        "cd webdev &&",
        "git show HEAD:sparkcore/graphql_schemas/third_party_schema.graphql > ../schema.graphql &&",
        "cd .. &&",
        "rm -rf webdev"
    )
}