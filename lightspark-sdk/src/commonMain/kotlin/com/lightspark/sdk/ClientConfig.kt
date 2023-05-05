package com.lightspark.sdk

import com.lightspark.sdk.core.auth.AuthProvider
import com.lightspark.sdk.model.BitcoinNetwork
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads

data class ClientConfig @JvmOverloads constructor(
    @set:JvmName("\$setServerUrl")
    var serverUrl: String = "api.lightspark.com",
    @set:JvmName("\$setDefaultBitcoinNetwork")
    var defaultBitcoinNetwork: BitcoinNetwork = BitcoinNetwork.MAINNET,
    @set:JvmName("\$setAuthProvider")
    var authProvider: AuthProvider? = null,
) {
    fun setServerUrl(serverUrl: String) = apply { this.serverUrl = serverUrl }
    fun setDefaultBitcoinNetwork(defaultBitcoinNetwork: BitcoinNetwork) =
        apply { this.defaultBitcoinNetwork = defaultBitcoinNetwork }

    fun setAuthProvider(authProvider: AuthProvider) = apply { this.authProvider = authProvider }
}
