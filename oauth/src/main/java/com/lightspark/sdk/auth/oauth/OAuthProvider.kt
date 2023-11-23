package com.lightspark.sdk.auth.oauth

import android.util.Log
import com.lightspark.sdk.core.auth.AuthProvider
import net.openid.appauth.AuthorizationException
import java.util.concurrent.CancellationException

class OAuthProvider(private val oAuthHelper: OAuthHelper) : AuthProvider {
    override fun withValidAuthToken(block: (String) -> Unit) {
        oAuthHelper.withAuthToken { token, _ -> block(token) }
    }

    override suspend fun getCredentialHeaders(): Map<String, String> =
        try {
            val accessToken = oAuthHelper.getFreshAuthToken()
            mapOf("Authorization" to "Bearer $accessToken")
        } catch (e: AuthorizationException) {
            Log.e("OAuthTokenProvider", "Failed to get fresh auth token. Trying no auth.", e)
            emptyMap()
        } catch (e: IllegalStateException) {
            Log.e("OAuthTokenProvider", "Failed to get fresh auth token. Trying no auth.", e)
            emptyMap()
        } catch (e: CancellationException) {
            Log.e("OAuthTokenProvider", "Failed to get fresh auth token. Trying no auth.", e)
            emptyMap()
        }

    override fun isAccountAuthorized() = oAuthHelper.isAuthorized()
}
