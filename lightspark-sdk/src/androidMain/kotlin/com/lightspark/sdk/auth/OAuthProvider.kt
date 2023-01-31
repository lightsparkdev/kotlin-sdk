package com.lightspark.sdk.auth

import android.util.Log

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
