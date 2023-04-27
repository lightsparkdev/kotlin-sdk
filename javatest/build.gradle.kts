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
        testImplementation("com.google.code.gson:gson:2.10.1")
        testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
        implementation(project(":lightspark-server-sdk"))
        implementation(project(":lightspark-core"))
    }
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}
