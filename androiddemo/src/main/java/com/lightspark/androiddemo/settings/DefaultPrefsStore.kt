package com.lightspark.androiddemo.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lightspark.sdk.model.BitcoinNetwork
import com.lightspark.sdk.requester.ServerEnvironment
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

data class SavedPrefs(
    val defaultWalletNodeId: String?,
    val bitcoinNetwork: BitcoinNetwork,
    val environment: ServerEnvironment,
) {
    companion object {
        val DEFAULT = SavedPrefs(
            defaultWalletNodeId = null,
            bitcoinNetwork = BitcoinNetwork.MAINNET,
            environment = ServerEnvironment.PROD,
        )
    }
}

class DefaultPrefsStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ls-preferences")
    private val DEFAULT_WALLET_NODE_ID = stringPreferencesKey("wallet_node_id")
    private val BITCOIN_NETWORK = stringPreferencesKey("bitcoin_network")
    private val SERVER_ENVIRONMENT = stringPreferencesKey("server_environment")

    suspend fun setAll(savedPrefs: SavedPrefs) {
        context.dataStore.edit { preferences ->
            savedPrefs.defaultWalletNodeId?.let { preferences[DEFAULT_WALLET_NODE_ID] = it }
                ?: preferences.remove(DEFAULT_WALLET_NODE_ID)
            preferences[BITCOIN_NETWORK] = savedPrefs.bitcoinNetwork.name
            preferences[SERVER_ENVIRONMENT] = savedPrefs.environment.name
        }
    }

    suspend fun getPrefsSync(): SavedPrefs {
        val preferences = context.dataStore.data.first()
        return SavedPrefs(
            preferences[DEFAULT_WALLET_NODE_ID],
            preferences[BITCOIN_NETWORK]?.let { BitcoinNetwork.valueOf(it) }
                ?: BitcoinNetwork.REGTEST,
            preferences[SERVER_ENVIRONMENT]?.let { ServerEnvironment.valueOf(it) }
                ?: ServerEnvironment.DEV,
        )
    }

    fun getPrefsFlow() = context.dataStore.data.map { preferences ->
        SavedPrefs(
            preferences[DEFAULT_WALLET_NODE_ID],
            preferences[BITCOIN_NETWORK]?.let { BitcoinNetwork.valueOf(it) }
                ?: BitcoinNetwork.REGTEST,
            preferences[SERVER_ENVIRONMENT]?.let { ServerEnvironment.valueOf(it) }
                ?: ServerEnvironment.DEV,
        )
    }.distinctUntilChanged()

    suspend fun setDefaultWalletNode(nodeId: String?) {
        context.dataStore.edit { preferences ->
            nodeId?.let { preferences[DEFAULT_WALLET_NODE_ID] = it }
                ?: preferences.remove(DEFAULT_WALLET_NODE_ID)
        }
    }

    suspend fun setBitcoinNetwork(bitcoinNetwork: BitcoinNetwork) {
        context.dataStore.edit { preferences ->
            preferences[BITCOIN_NETWORK] = bitcoinNetwork.name
        }
    }

    suspend fun setServerEnvironment(environment: ServerEnvironment) {
        context.dataStore.edit { preferences ->
            preferences[SERVER_ENVIRONMENT] = environment.name
        }
    }
}
