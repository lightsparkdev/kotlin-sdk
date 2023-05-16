pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://repo.repsy.io/mvn/chrynan/public") }
    }
}

rootProject.name = "Lightspark"
include(":lightspark-sdk")
include(":wallet-sdk")
include(":core")
include(":androidwalletdemo")
include(":javatest")
include(":oauth")
