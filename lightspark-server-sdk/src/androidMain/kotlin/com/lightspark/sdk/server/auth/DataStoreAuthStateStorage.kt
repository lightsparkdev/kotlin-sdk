package com.lightspark.sdk.server.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.openid.appauth.*

class DataStoreAuthStateStorage(private val context: Context) : AuthStateStorage {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)
    private val STATE_JSON_KEY = stringPreferencesKey("auth_state_json")
    private val currentAuthState = AtomicReference<AuthState>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun observeIsAuthorized() = context.dataStore.data.map { preferences ->
        preferences[STATE_JSON_KEY]?.let { AuthState.jsonDeserialize(it) }
    }.map {
        it?.isAuthorized ?: false
    }

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
        currentAuthState.set(state)
        writeState(state)
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

    // TODO: We should try to avoid runBlocking here and make everything in this interface suspended.
    private fun readState(): AuthState = runBlocking {
        val stateJson = context.dataStore.data.first()[STATE_JSON_KEY]
        stateJson?.let { AuthState.jsonDeserialize(it) } ?: AuthState()
    }

    private fun writeState(state: AuthState?) = coroutineScope.launch {
        context.dataStore.edit { preferences ->
            state?.let { preferences[STATE_JSON_KEY] = it.jsonSerializeString() }
                ?: preferences.remove(STATE_JSON_KEY)
        }
    }

    companion object {
        private const val STORE_NAME = "ls-oauth"
    }
}
