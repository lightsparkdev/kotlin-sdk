package com.lightspark.androiddemo

import com.lightspark.sdk.LightsparkClient

// This is a singleton that provides the LightsparkClient instance. Note that in most real apps,
// this should probably be injected via a dependency injection framework instead.
object LightsparkClientProvider {
    val client by lazy {
        LightsparkClient.Builder().build()
    }
}