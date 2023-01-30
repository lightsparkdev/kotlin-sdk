package com.lightspark.sdk.auth

interface AuthTokenProvider {
    fun withValidAuthToken(block: (String) -> Unit)
    suspend fun getCredentialHeaders(): Map<String, String>
}