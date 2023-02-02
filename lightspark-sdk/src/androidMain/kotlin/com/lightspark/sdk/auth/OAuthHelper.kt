package com.lightspark.sdk.auth

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.lightspark.sdk.model.ServerEnvironment
import kotlinx.coroutines.suspendCancellableCoroutine
import net.openid.appauth.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class OAuthHelper(
    context: Context,
    private val authStateStorage: AuthStateStorage = SharedPrefsAuthStateStorage(context),
    private var serverEnvironment: ServerEnvironment = ServerEnvironment.DEV
) {
    private val authService = AuthorizationService(context.applicationContext)

    private val authState: AuthState
        get() = authStateStorage.getCurrent()

    init {
        val savedAuthState = authStateStorage.getCurrent()
        if (savedAuthState.authorizationServiceConfiguration == null) {
            authStateStorage.replace(defaultAuthState())
        }
    }

    private fun defaultAuthState(): AuthState {
        val serviceConfig = when (serverEnvironment) {
            ServerEnvironment.DEV -> AuthorizationServiceConfiguration(
                Uri.parse("https://dev.dev.sparkinfra.net/oauth/authorize"),
                Uri.parse("https://api.dev.dev.sparkinfra.net/oauth/token")
            )
            ServerEnvironment.PROD -> AuthorizationServiceConfiguration(
                Uri.parse("https://app.lightspark.com/oauth/authorize"),
                Uri.parse("https://api.lightspark.com/oauth/token")
            )
        }
        return AuthState(serviceConfig)
    }

    fun setServerEnvironment(environment: ServerEnvironment): Boolean {
        if (serverEnvironment == environment) return false
        serverEnvironment = environment
        if (authStateStorage.getCurrent().authorizationServiceConfiguration?.authorizationEndpoint !=
            defaultAuthState().authorizationServiceConfiguration?.authorizationEndpoint
        ) {
            authStateStorage.replace(defaultAuthState())
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
        clientSecret: String,
        callback: (String?, Exception?) -> Unit
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
                            put("client_secret", clientSecret)
                        }
                }
            }
        ) { response, exception ->
            authStateStorage.updateAfterTokenResponse(response, exception)
            callback(response?.refreshToken, exception)
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