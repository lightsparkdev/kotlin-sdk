pluginManagement {
    repositories {
        mavenLocal()
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        mavenLocal {
            content {
                includeGroup("me.uma")
            }
            metadataSources {
                mavenPom()
                artifact()
            }
        }
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "Lightspark"
include(":lightspark-sdk")
include(":wallet-sdk")
include(":core")
include(":androidwalletdemo")
include(":javatest")
include(":oauth")
include(":crypto")
include(":umaserverdemo")
include(":remotesignerdemo")
