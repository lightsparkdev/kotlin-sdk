import io.ktor.plugin.features.DockerEnvironmentVariable
import io.ktor.plugin.features.JreVersion

plugins {
    kotlin("jvm")
    id("io.ktor.plugin") version "2.3.3"
    alias(libs.plugins.kotlinSerialization)
}

group = "com.lightspark"
version = "0.0.1"

task("prepareKotlinIdeaImport") {}
task("prepareKotlinBuildScriptModel") {}

application {
    mainClass.set("com.lightspark.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    docker {
//        jreVersion.set(JreVersion.JRE_17)
//        environmentVariables.add(DockerEnvironmentVariable("LIGHTSPARK_API_TOKEN_CLIENT_ID", "lightspark"))
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-client-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-compression-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation(project(":lightspark-sdk"))
    implementation(project(":core"))
    implementation(project(":crypto"))
    implementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation(libs.kotlin.test.junit)
}
