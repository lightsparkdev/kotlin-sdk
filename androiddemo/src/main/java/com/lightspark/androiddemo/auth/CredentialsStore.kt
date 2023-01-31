package com.lightspark.androiddemo.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lightspark.androiddemo.LightsparkDemoApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CredentialsStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ls-credentials")
    private val TOKEN_ID_KEY = stringPreferencesKey("token_id")
    private val TOKEN_SECRET_KEY = stringPreferencesKey("token_secret")

    suspend fun setAccountToken(tokenId: String, tokenSecret: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_ID_KEY] = tokenId
            preferences[TOKEN_SECRET_KEY] = tokenSecret
        }
    }

    suspend fun getAccountTokenSync(): Pair<String, String>? {
        val preferences = context.dataStore.data.first()
        val tokenId = preferences[TOKEN_ID_KEY] ?: return null
        val tokenSecret = preferences[TOKEN_SECRET_KEY] ?: return null
        return Pair(tokenId, tokenSecret)
    }

    fun getAccountTokenFlow() = context.dataStore.data.map { preferences ->
        val tokenId = preferences[TOKEN_ID_KEY] ?: return@map null
        val tokenSecret = preferences[TOKEN_SECRET_KEY] ?: return@map null
        Pair(tokenId, tokenSecret)
    }

    companion object {
        val instance by lazy {
            CredentialsStore(LightsparkDemoApplication.instance)
        }
    }
}