package com.lightspark.sdk.requester

enum class ServerEnvironment(val graphQLUrl: String) {
    DEV("api.dev.dev.sparkinfra.net"),
    PROD("api.lightspark.com"),
}
