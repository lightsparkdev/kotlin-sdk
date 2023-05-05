package com.lightspark.sdk.wallet.auth.jwt

import com.lightspark.sdk.core.auth.AuthProvider
import com.lightspark.sdk.core.auth.LightsparkAuthenticationException
import kotlinx.datetime.Clock

/**
 * A custom [AuthProvider] that uses a JWT token to authenticate requests.
 *
 * Should generally not be used directly by clients, but rather through the [loginWithJwt] method of a
 * [LightsparkWalletClient].
 *
 * @param tokenStorage A [JwtStorage] implementation that stores or retrieves the current JWT token info.
 */
class CustomJwtAuthProvider(private val tokenStorage: JwtStorage) : AuthProvider {
    private var tokenInfo: JwtTokenInfo? = tokenStorage.getCurrent()

    /**
     * Sets the current JWT token info and saves it in the [tokenStorage] asynchronously.
     *
     * @param tokenInfo The new token info.
     */
    fun setTokenInfo(tokenInfo: JwtTokenInfo?) {
        this.tokenInfo = tokenInfo
        tokenStorage.replace(tokenInfo)
    }

    override fun withValidAuthToken(block: (String) -> Unit) {
        tokenInfo?.let { block(it.accessToken) } ?: throw LightsparkAuthenticationException("No valid jwt access token")
    }

    override suspend fun getCredentialHeaders(): Map<String, String> {
        return tokenInfo?.let { mapOf("Authorization" to "Bearer ${it.accessToken}") } ?: emptyMap()
    }

    override fun isAccountAuthorized(): Boolean {
        return tokenInfo?.let { Clock.System.now() <= it.validUntil } ?: false
    }
}
