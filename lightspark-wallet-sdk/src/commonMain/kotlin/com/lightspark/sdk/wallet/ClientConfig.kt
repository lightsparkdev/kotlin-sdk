package com.lightspark.sdk.wallet

import com.lightspark.sdk.core.auth.AuthProvider
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads

data class ClientConfig @JvmOverloads constructor(
    @set:JvmName("\$setServerUrl")
    var serverUrl: String = "api.lightspark.com",
    @set:JvmName("\$setAuthProvider")
    var authProvider: AuthProvider? = null,
) {
    fun setServerUrl(serverUrl: String) = apply { this.serverUrl = serverUrl }
    fun setAuthProvider(authProvider: AuthProvider) = apply { this.authProvider = authProvider }
}
