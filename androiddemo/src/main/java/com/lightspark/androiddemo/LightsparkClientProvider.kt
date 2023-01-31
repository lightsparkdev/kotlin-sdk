package com.lightspark.androiddemo

import com.lightspark.sdk.LightsparkClient
import com.lightspark.sdk.LightsparkWalletClient

// This is a singleton that provides the LightsparkClient instance. Note that in most real apps,
// this should probably be injected via a dependency injection framework instead.
object LightsparkClientProvider {
    val fullClient by lazy {
        LightsparkClient.Builder().build()
    }

    val walletClient by lazy {
        LightsparkWalletClient.Builder()
            .fullLightsparkClient(fullClient)
            .build()
    }
}