import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.mgd.core.gradle.S3Upload
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.io.FileOutputStream

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.apollo)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.dokka)
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
                implementation(libs.apollo.runtime)
                implementation(libs.apollo.normalized.cache)
                implementation(libs.kase64)
                implementation(libs.kotlin.serialization.json)
                implementation(libs.krypt)
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
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
        mapScalarToKotlinLong("Long")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf(
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    )
}

buildkonfig {
    packageName = "com.lightspark.conf"

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

/**
 * Pulls the graphQL schema from the webdev repo and saves it in the right location in the project.
 *
 * NOTE: This is a pretty hacky way to do this. We should probably be exporting the schema out to some artifact registry or something instead of trying to pull it from github.
 */
tasks.register<Exec>("updateGraphQLSchema") {
    group = "build"
    workingDir = File(projectDir, "src/commonMain/graphql")
    commandLine =
        "git clone --no-checkout --depth=1 git@github.com:lightsparkdev/webdev.git".split(" ")
    doLast {
        exec {
            commandLine =
                "git show HEAD:sparkcore/graphql_schemas/third_party_schema.graphql".split(" ")
            workingDir = File(projectDir, "src/commonMain/graphql/webdev")
            standardOutput = FileOutputStream("src/commonMain/graphql/schema.graphqls")
        }
        exec {
            commandLine = listOf("rm", "-rf", "webdev")
            workingDir = File(projectDir, "src/commonMain/graphql")
        }
    }
}
