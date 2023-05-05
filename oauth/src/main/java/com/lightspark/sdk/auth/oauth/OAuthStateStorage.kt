package com.lightspark.sdk.auth.oauth

import net.openid.appauth.*

interface OAuthStateStorage {
    fun getCurrent(): AuthState
    fun replace(state: AuthState?): AuthState?
    fun updateAfterAuthorization(
        response: AuthorizationResponse?,
        ex: AuthorizationException?,
    ): AuthState?

    fun updateAfterTokenResponse(
        response: TokenResponse?,
        ex: AuthorizationException?,
    ): AuthState?

    fun updateAfterRegistration(
        response: RegistrationResponse?,
        ex: AuthorizationException?,
    ): AuthState?
}
