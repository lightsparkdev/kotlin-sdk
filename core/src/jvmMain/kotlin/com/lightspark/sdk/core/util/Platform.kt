package com.lightspark.sdk.core.util

open class JvmPlatform : Platform {
    override val platformName: String = "JVM"
    override val version: String = System.getProperty("java.version")
    override fun getEnv(name: String): String? {
        return System.getenv(name)
    }
}

actual fun getPlatform(): Platform = JvmPlatform()
