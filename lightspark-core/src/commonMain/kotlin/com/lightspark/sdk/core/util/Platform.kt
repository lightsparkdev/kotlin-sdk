package com.lightspark.sdk.core.util

interface Platform {
    val platformName: String
    val version: String
    fun getEnv(name: String): String?
}

expect fun getPlatform(): Platform
