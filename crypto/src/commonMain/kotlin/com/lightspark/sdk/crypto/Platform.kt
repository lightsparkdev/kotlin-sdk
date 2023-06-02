package com.lightspark.sdk.crypto

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
