package com.lightspark.sdk.core.auth

interface AuthProvider {
    fun withValidAuthToken(block: (String) -> Unit)
    suspend fun getCredentialHeaders(): Map<String, String>
    fun isAccountAuthorized(): Boolean
}
