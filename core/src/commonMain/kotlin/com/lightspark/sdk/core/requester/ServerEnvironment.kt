package com.lightspark.sdk.core.requester

enum class ServerEnvironment(val graphQLUrl: String) {
    DEV("api.dev.dev.sparkinfra.net"),
    PROD("api.lightspark.com"),
}
