package com.lightspark.androiddemo.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lightspark.androiddemo.LightsparkDemoApplication
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

data class SavedPrefs(
    val defaultWalletNodeId: String?
)

class DefaultPrefsStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ls-preferences")
    private val DEFAULT_WALLET_NODE_ID = stringPreferencesKey("wallet_node_id")

    suspend fun setAll(savedPrefs: SavedPrefs) {
        context.dataStore.edit { preferences ->
            savedPrefs.defaultWalletNodeId?.let { preferences[DEFAULT_WALLET_NODE_ID] = it }
                ?: preferences.remove(DEFAULT_WALLET_NODE_ID)
        }
    }

    suspend fun getPrefsSync(): SavedPrefs {
        val preferences = context.dataStore.data.first()
        return SavedPrefs(preferences[DEFAULT_WALLET_NODE_ID])
    }

    fun getPrefsFlow() = context.dataStore.data.map { preferences ->
        SavedPrefs(preferences[DEFAULT_WALLET_NODE_ID])
    }.distinctUntilChanged()

    companion object {
        val instance by lazy {
            DefaultPrefsStore(LightsparkDemoApplication.instance)
        }
    }
}