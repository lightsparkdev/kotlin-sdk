package com.lightspark.sdk.core.util

class AndroidPlatform : Platform {
    override val platformName: String = "Android"
    override val version: String = android.os.Build.VERSION.SDK_INT.toString()
    override fun getEnv(name: String): String? {
        return System.getenv(name)
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()
