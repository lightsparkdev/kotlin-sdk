package com.lightspark.sdk.wallet.auth.oauth

import android.util.Log
import com.lightspark.sdk.core.auth.AuthProvider

class OAuthProvider(private val oAuthHelper: OAuthHelper) : AuthProvider {
    override fun withValidAuthToken(block: (String) -> Unit) {
        oAuthHelper.withAuthToken { token, _ -> block(token) }
    }

    override suspend fun getCredentialHeaders(): Map<String, String> =
        try {
            val accessToken = oAuthHelper.getFreshAuthToken()
            mapOf("Authorization" to "Bearer $accessToken")
        } catch (e: Exception) {
            Log.e("OAuthTokenProvider", "Failed to get fresh auth token. Trying no auth.", e)
            emptyMap()
        }

    override fun isAccountAuthorized() = oAuthHelper.isAuthorized()
}
