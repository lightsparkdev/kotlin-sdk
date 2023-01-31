package com.lightspark.androiddemo

import com.lightspark.sdk.LightsparkClient
import com.lightspark.sdk.LightsparkWalletClient

// This is a singleton that provides the LightsparkClient instance. Note that in most real apps,
// this should probably be injected via a dependency injection framework instead.
object LightsparkClientProvider {
    val fullClient by lazy {
        LightsparkClient.Builder()
//            .tokenId("faketokentokillwallet")
            .build()
    }

    val walletClient by lazy {
        LightsparkWalletClient.Builder()
            .walletId("LightsparkNode:0185c269-8aa3-f96b-0000-0ae100b58599")
            .fullLightsparkClient(fullClient)
            .build()
    }
}