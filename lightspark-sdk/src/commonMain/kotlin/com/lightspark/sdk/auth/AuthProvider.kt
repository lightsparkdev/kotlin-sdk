package com.lightspark.sdk.auth

interface AuthProvider {
    fun withValidAuthToken(block: (String) -> Unit)
    suspend fun getCredentialHeaders(): Map<String, String>
    fun isAccountAuthorized(): Boolean
}
