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
    }
}

rootProject.name = "Lightspark"
include(":lightspark-sdk")
include(":core")
include(":javatest")
include(":oauth")
include(":crypto")
include(":umaserverdemo")
include(":remotesignerdemo")
