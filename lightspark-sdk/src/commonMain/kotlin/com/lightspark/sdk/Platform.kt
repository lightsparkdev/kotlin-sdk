package com.lightspark.sdk

internal interface Platform {
    val name: String
}

internal expect fun getPlatform(): Platform