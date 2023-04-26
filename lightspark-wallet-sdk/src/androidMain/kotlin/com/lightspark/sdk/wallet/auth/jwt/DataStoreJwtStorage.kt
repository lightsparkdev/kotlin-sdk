package com.lightspark.sdk.wallet.auth.jwt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lightspark.sdk.wallet.util.serializerFormat
import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class DataStoreJwtStorage(private val context: Context) : JwtStorage {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)
    private val STATE_JSON_KEY = stringPreferencesKey("jwt_state_json")
    private val currentJwtInfo = AtomicReference<JwtTokenInfo?>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getCurrent(): JwtTokenInfo? {
        return currentJwtInfo.get() ?: run {
            val state = readState()
            if (currentJwtInfo.compareAndSet(null, state)) {
                state
            } else {
                currentJwtInfo.get()
            }
        }
    }

    override fun replace(state: JwtTokenInfo?) {
        currentJwtInfo.set(state)
        writeState(state)
    }

    // TODO: We should try to avoid runBlocking here and make everything in this interface suspended.
    private fun readState(): JwtTokenInfo? = runBlocking {
        val stateJson = context.dataStore.data.first()[STATE_JSON_KEY]
        stateJson?.let { serializerFormat.decodeFromString(it) }
    }

    private fun writeState(state: JwtTokenInfo?) = coroutineScope.launch {
        context.dataStore.edit { preferences ->
            state?.let { preferences[STATE_JSON_KEY] = serializerFormat.encodeToString(state) }
                ?: preferences.remove(STATE_JSON_KEY)
        }
    }

    companion object {
        private const val STORE_NAME = "ls-jwt"
    }
}
