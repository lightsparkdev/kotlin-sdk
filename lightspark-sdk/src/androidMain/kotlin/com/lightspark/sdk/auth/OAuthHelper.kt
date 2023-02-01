package com.lightspark.sdk.auth

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.suspendCancellableCoroutine
import net.openid.appauth.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class OAuthHelper(
    context: Context,
    private val authStateStorage: AuthStateStorage = SharedPrefsAuthStateStorage(context)
) {
    private val authService = AuthorizationService(context.applicationContext)

    private val authState: AuthState
        get() = authStateStorage.getCurrent()

    init {
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://dev.dev.sparkinfra.net/oauth/authorize"),
            Uri.parse("https://api.dev.dev.sparkinfra.net/oauth/token")
        )
        val savedAuthState = authStateStorage.getCurrent()
        if (savedAuthState.authorizationServiceConfiguration == null) {
            authStateStorage.replace(AuthState(serviceConfig))
        }
    }

    fun isAuthorized() = authState.isAuthorized

    fun launchAuthFlow(
        clientId: String,
        redirectUri: String,
        completedIntent: PendingIntent,
        canceledIntent: PendingIntent? = null,
    ) {
        val authRequest = AuthorizationRequest.Builder(
            requireNotNull(authState.authorizationServiceConfiguration) {
                "Authorization service configuration is null"
            },
            clientId,
            ResponseTypeValues.CODE,
            Uri.parse(redirectUri)
        )
            .setScope("all") // TODO: Replace with actual scopes as needed
            .build()

        authService.performAuthorizationRequest(
            authRequest, completedIntent, canceledIntent ?: completedIntent
        )
    }

    fun handleAuthResponse(intent: Intent) {
        val response = AuthorizationResponse.fromIntent(intent)
        val ex = AuthorizationException.fromIntent(intent)
        authStateStorage.updateAfterAuthorization(response, ex)
        if (response == null) {
            throw ex ?: IllegalStateException("Authorization response is null")
        }
    }

    fun fetchAndPersistRefreshToken(
        callback: (String?) -> Unit
    ) {
        val authorizationResponse = requireNotNull(authState.lastAuthorizationResponse) {
            "Authorization response is null. Call handleAuthResponse() first."
        }
        authService.performTokenRequest(
            authorizationResponse.createTokenExchangeRequest(),
            object : ClientAuthentication {
                override fun getRequestHeaders(clientId: String): MutableMap<String, String> {
                    return mutableMapOf(BETA_HEADER_KEY to BETA_HEADER_VALUE)
                }

                override fun getRequestParameters(clientId: String): MutableMap<String, String> {
                    return NoClientAuthentication.INSTANCE.getRequestParameters(clientId)
                        .toMutableMap().apply {
                            put("client_secret", "")
                        }
                }
            }
        ) { response, exception ->
            authStateStorage.updateAfterTokenResponse(response, exception)
            // TODO(Jeremy): I'm not sure re-throwing is the right thing to do here, since it's not
            // catchable by the caller. Maybe we should just log the exception?
            if (exception != null) throw exception
            callback(response?.refreshToken)
        }
    }

    fun withAuthToken(block: (String, String) -> Unit) {
        authState.performActionWithFreshTokens(authService) { accessToken, idToken, ex ->
            authStateStorage.replace(authState)
            if (accessToken != null) {
                block(
                    accessToken,
                    requireNotNull(idToken) { "ID token is null when access token is not" }
                )
            } else {
                throw ex ?: IllegalStateException("Authorization response is null")
            }
        }
    }

    suspend fun getFreshAuthToken(): String {
        return suspendCancellableCoroutine { continuation ->
            authState.performActionWithFreshTokens(authService) { accessToken, _, ex ->
                authStateStorage.replace(authState)
                if (accessToken != null) {
                    continuation.resume(accessToken)
                } else {
                    continuation.resumeWithException(
                        ex ?: IllegalStateException("Authorization response is null")
                    )
                }
            }
        }
    }

    fun endSession(
        completedIntent: PendingIntent,
        redirectUri: String? = null,
        canceledIntent: PendingIntent? = null,
    ) {
        val serviceConfig = authState.authorizationServiceConfiguration ?: return
        val endSessionRequest = EndSessionRequest.Builder(serviceConfig)
            .setIdTokenHint(authState.idToken)
            .setPostLogoutRedirectUri(redirectUri.let { Uri.parse(it) })
            .build()
        authService.performEndSessionRequest(
            endSessionRequest,
            completedIntent,
            canceledIntent ?: completedIntent
        )
    }

    fun parseEndSessionResponse(intent: Intent): String {
        val response = EndSessionResponse.fromIntent(intent)
        val ex = AuthorizationException.fromIntent(intent)
        if (response != null) {
            return response.state ?: ""
        } else {
            throw ex ?: IllegalStateException("End session response is null")
        }
    }
}