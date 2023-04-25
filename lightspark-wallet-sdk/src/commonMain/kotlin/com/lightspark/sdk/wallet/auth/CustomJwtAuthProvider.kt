package com.lightspark.sdk.wallet.auth

import com.lightspark.sdk.core.auth.AuthProvider
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

/**
 * A custom [AuthProvider] that uses a JWT token to authenticate requests.
 *
 * Should generally not be used directly by clients, but rather through the [loginWithJwt] method of a
 * [LightsparkWalletClient].
 *
 * @param accessToken The JWT token to use for authentication.
 * @param validUntil The time at which the token expires.
 */
class CustomJwtAuthProvider(private val accessToken: String, private val validUntil: Instant) : AuthProvider {
    private val headerMap by lazy { mapOf("Authorization" to "Bearer $accessToken") }

    override fun withValidAuthToken(block: (String) -> Unit) {
        block(accessToken)
    }

    override suspend fun getCredentialHeaders(): Map<String, String> {
        return headerMap
    }

    override fun isAccountAuthorized(): Boolean {
        return Clock.System.now() <= validUntil
    }
}
