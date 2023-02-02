package com.lightspark.sdk.model

enum class ServerEnvironment(val graphQLUrl: String) {
    DEV("https://api.dev.dev.sparkinfra.net/graphql/2023-01-01"),
    PROD("https://api.lightspark.com/graphql/2023-01-01")
}