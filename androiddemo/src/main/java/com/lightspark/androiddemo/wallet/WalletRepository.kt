package com.lightspark.androiddemo.wallet

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.androiddemo.settings.DefaultPrefsStore
import com.lightspark.androiddemo.settings.SavedPrefs
import com.lightspark.sdk.LightsparkWalletClient
import com.lightspark.sdk.wrapWithLceFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WalletRepository(
    private val walletClient: LightsparkWalletClient = LightsparkClientProvider.walletClient,
    private val prefsStore: DefaultPrefsStore = DefaultPrefsStore.instance
) {
    fun getWalletDashboard() = wrapWithLceFlow { walletClient.getWalletDashboard() }

    val activeWalletId
        get() = walletClient.activeWalletId

    fun unlockWallet(password: String) =
        wrapWithLceFlow { walletClient.unlockActiveWallet(password) }

    fun setActiveWalletAndUnlock(nodeId: String, password: String) =
        wrapWithLceFlow {
            if (walletClient.unlockWallet(nodeId, password)) {
                prefsStore.setAll(SavedPrefs(nodeId))
                true
            } else {
                false
            }
        }

    suspend fun setActiveWalletWithoutUnlocking(nodeId: String) = withContext(Dispatchers.IO) {
        walletClient.setActiveWalletWithoutUnlocking(nodeId)
        prefsStore.setAll(SavedPrefs(nodeId))
    }

    val isWalletUnlocked = walletClient.observeWalletUnlocked()
}