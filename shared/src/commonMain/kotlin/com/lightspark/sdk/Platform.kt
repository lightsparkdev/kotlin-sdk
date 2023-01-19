package com.lightspark.sdk

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform