package com.lightspark.sdk.wallet.auth.oauth

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.lightspark.sdk.core.requester.ServerEnvironment
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.EndSessionResponse
import net.openid.appauth.ResponseTypeValues

class OAuthHelper(
    context: Context,
    private val OAuthStateStorage: OAuthStateStorage = SharedPrefsOAuthStateStorage(context),
    private var serverEnvironment: ServerEnvironment = ServerEnvironment.DEV,
) {
    private val authService = AuthorizationService(context.applicationContext)

    private val authState: AuthState
        get() = OAuthStateStorage.getCurrent()

    init {
        val savedAuthState = OAuthStateStorage.getCurrent()
        if (savedAuthState.authorizationServiceConfiguration == null) {
            OAuthStateStorage.replace(defaultAuthState())
        }
    }

    private fun defaultAuthState(): AuthState {
        val serviceConfig = when (serverEnvironment) {
            ServerEnvironment.DEV -> AuthorizationServiceConfiguration(
                Uri.parse("https://dev.dev.sparkinfra.net/oauth/authorize"),
                Uri.parse("https://api.dev.dev.sparkinfra.net/oauth/token"),
            )

            ServerEnvironment.PROD -> AuthorizationServiceConfiguration(
                Uri.parse("https://app.lightspark.com/oauth/authorize"),
                Uri.parse("https://api.lightspark.com/oauth/token"),
            )
        }
        return AuthState(serviceConfig)
    }

    fun setServerEnvironment(environment: ServerEnvironment): Boolean {
        if (serverEnvironment == environment) return false
        serverEnvironment = environment
        if (OAuthStateStorage.getCurrent().authorizationServiceConfiguration?.authorizationEndpoint !=
            defaultAuthState().authorizationServiceConfiguration?.authorizationEndpoint
        ) {
            OAuthStateStorage.replace(defaultAuthState())
            return true
        }
        return false
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
            Uri.parse(redirectUri),
        )
            .setScope("all") // TODO: Replace with actual scopes as needed
            .build()

        authService.performAuthorizationRequest(
            authRequest,
            completedIntent,
            canceledIntent ?: completedIntent,
        )
    }

    fun handleAuthResponseAndRequestToken(
        response: Intent,
        clientSecret: String,
        callback: (String?, Exception?) -> Unit,
    ) {
        try {
            handleAuthResponse(response)
        } catch (e: Exception) {
            callback(null, e)
            return
        }
        fetchAndPersistRefreshToken(clientSecret, callback)
    }

    fun handleAuthResponse(intent: Intent) {
        val response = AuthorizationResponse.fromIntent(intent)
        val ex = AuthorizationException.fromIntent(intent)
        OAuthStateStorage.updateAfterAuthorization(response, ex)
        if (response == null) {
            throw ex ?: IllegalStateException("Authorization response is null")
        }
    }

    fun fetchAndPersistRefreshToken(
        clientSecret: String,
        callback: (String?, Exception?) -> Unit,
    ) {
        val authorizationResponse = requireNotNull(authState.lastAuthorizationResponse) {
            "Authorization response is null. Call handleAuthResponse() first."
        }
        authService.performTokenRequest(
            authorizationResponse.createTokenExchangeRequest(),
            OAuthCustomClientAuthentication(clientSecret),
        ) { response, exception ->
            OAuthStateStorage.updateAfterTokenResponse(response, exception)
            callback(response?.refreshToken, exception)
        }
    }

    fun withAuthToken(block: (String, String) -> Unit) {
        authState.performActionWithFreshTokens(authService) { accessToken, idToken, ex ->
            OAuthStateStorage.replace(authState)
            if (accessToken != null) {
                block(
                    accessToken,
                    requireNotNull(idToken) { "ID token is null when access token is not" },
                )
            } else {
                throw ex ?: IllegalStateException("Authorization response is null")
            }
        }
    }

    suspend fun getFreshAuthToken(): String {
        return suspendCancellableCoroutine { continuation ->
            authState.performActionWithFreshTokens(authService) { accessToken, _, ex ->
                OAuthStateStorage.replace(authState)
                if (accessToken != null) {
                    continuation.resume(accessToken)
                } else {
                    continuation.resumeWithException(
                        ex ?: IllegalStateException("Authorization response is null"),
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
            canceledIntent ?: completedIntent,
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
