plugins {
    id("java-library")
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    dependencies {
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${libs.versions.kotlinCoroutines.get()}")
        testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
        implementation(project(":lightspark-sdk"))
    }
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}
