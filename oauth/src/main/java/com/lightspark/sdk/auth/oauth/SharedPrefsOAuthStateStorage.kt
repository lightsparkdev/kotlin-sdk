package com.lightspark.sdk.auth.oauth

import android.content.Context
import java.util.concurrent.atomic.AtomicReference
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.RegistrationResponse
import net.openid.appauth.TokenResponse

class SharedPrefsOAuthStateStorage(context: Context) : OAuthStateStorage {
    private val authPrefs = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE)
    private val currentAuthState = AtomicReference<AuthState>()

    override fun getCurrent(): AuthState {
        return currentAuthState.get() ?: run {
            val state = readState()
            if (currentAuthState.compareAndSet(null, state)) {
                state
            } else {
                currentAuthState.get()
            }
        }
    }

    override fun replace(state: AuthState?): AuthState? {
        val prevState = currentAuthState.getAndSet(state)
        if (prevState != state) {
            writeState(state)
        }
        return state
    }

    override fun updateAfterAuthorization(
        response: AuthorizationResponse?,
        ex: AuthorizationException?,
    ): AuthState? {
        val current = getCurrent()
        current.update(response, ex)
        return replace(current)
    }

    override fun updateAfterTokenResponse(
        response: TokenResponse?,
        ex: AuthorizationException?,
    ): AuthState? {
        val current = getCurrent()
        current.update(response, ex)
        return replace(current)
    }

    override fun updateAfterRegistration(
        response: RegistrationResponse?,
        ex: AuthorizationException?,
    ): AuthState? {
        val current = getCurrent()
        if (ex != null) {
            return current
        }
        current.update(response)
        return replace(current)
    }

    private fun readState(): AuthState {
        val stateJson = authPrefs.getString(STATE_JSON_KEY, null)
        return stateJson?.let { AuthState.jsonDeserialize(it) } ?: AuthState()
    }

    private fun writeState(state: AuthState?) {
        val authPrefsEditor = authPrefs.edit()
        if (state == null) {
            authPrefsEditor.remove(STATE_JSON_KEY)
        } else {
            authPrefsEditor.putString(STATE_JSON_KEY, state.jsonSerializeString())
        }
        authPrefsEditor.apply()
    }

    companion object {
        private const val STORE_NAME = "auth"
        private const val STATE_JSON_KEY = "stateJson"
    }
}
