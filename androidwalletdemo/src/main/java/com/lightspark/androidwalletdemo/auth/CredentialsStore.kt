package com.lightspark.androidwalletdemo.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

data class SavedCredentials(
    val accountId: String,
    val jwt: String,
)

class CredentialsStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ls-jwt-info")
    private val ACCOUNT_ID_KEY = stringPreferencesKey("account_id")
    private val JWT_KEY = stringPreferencesKey("jwt")

    suspend fun setAccountData(tokenId: String, tokenSecret: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCOUNT_ID_KEY] = tokenId
            preferences[JWT_KEY] = tokenSecret
        }
    }

    suspend fun clear(): Boolean {
        val hadToken = context.dataStore.data.firstOrNull()?.let { it[ACCOUNT_ID_KEY] } != null
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
        return hadToken
    }

    fun getJwtInfoFlow() = context.dataStore.data.map { preferences ->
        val accountId = preferences[ACCOUNT_ID_KEY] ?: return@map null
        val jwt = preferences[JWT_KEY] ?: return@map null
        SavedCredentials(accountId, jwt)
    }.distinctUntilChanged()
}
