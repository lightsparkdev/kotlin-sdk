package com.lightspark.sdk.auth

/**
 * A stub implementation of [AuthProvider] that does not provide any authentication. It always stays unauthorized.
 */
class StubAuthProvider : AuthProvider {
    override fun withValidAuthToken(block: (String) -> Unit) {
        // Do nothing
    }

    override suspend fun getCredentialHeaders() = emptyMap<String, String>()

    override fun isAccountAuthorized() = false
}
