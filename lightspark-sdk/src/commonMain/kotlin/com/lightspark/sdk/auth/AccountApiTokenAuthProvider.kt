package com.lightspark.sdk.auth

import saschpe.kase64.base64Encoded

class AccountApiTokenAuthProvider(tokenId: String, tokenSecret: String) : AuthProvider {
    private val authToken = "$tokenId:$tokenSecret".base64Encoded
    private val headerMap by lazy { mapOf("Authorization" to "Basic $authToken") }

    override fun withValidAuthToken(block: (String) -> Unit) {
        block(authToken)
    }

    override suspend fun getCredentialHeaders(): Map<String, String> {
        return headerMap
    }

    override fun isAccountAuthorized(): Boolean {
        return true
    }
}