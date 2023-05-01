pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://repo.repsy.io/mvn/chrynan/public") }
    }
}

rootProject.name = "Lightspark"
include(":lightspark-server-sdk")
include(":lightspark-wallet-sdk")
include(":lightspark-core")
include(":androidwalletdemo")
include(":javatest")
include(":lightspark-oauth")
