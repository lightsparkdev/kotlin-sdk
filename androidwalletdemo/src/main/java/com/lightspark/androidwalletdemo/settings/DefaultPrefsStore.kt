package com.lightspark.androidwalletdemo.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lightspark.sdk.core.requester.ServerEnvironment
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

data class SavedPrefs(
    val environment: ServerEnvironment,
) {
    companion object {
        val DEFAULT = SavedPrefs(
            environment = ServerEnvironment.PROD,
        )
    }
}

class DefaultPrefsStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ls-preferences")
    private val SERVER_ENVIRONMENT = stringPreferencesKey("server_environment")

    fun getPrefsSync(): SavedPrefs = runBlocking {
        val preferences = context.dataStore.data.first()
        SavedPrefs(
            preferences[SERVER_ENVIRONMENT]?.let { ServerEnvironment.valueOf(it) }
                ?: ServerEnvironment.PROD,
        )
    }

    fun getPrefsFlow() = context.dataStore.data.map { preferences ->
        SavedPrefs(
            preferences[SERVER_ENVIRONMENT]?.let { ServerEnvironment.valueOf(it) }
                ?: ServerEnvironment.PROD,
        )
    }.distinctUntilChanged()

    suspend fun setServerEnvironment(environment: ServerEnvironment) {
        context.dataStore.edit { preferences ->
            preferences[SERVER_ENVIRONMENT] = environment.name
        }
    }
}
