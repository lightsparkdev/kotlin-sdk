package com.lightspark.androiddemo.wallet

import com.lightspark.androiddemo.settings.DefaultPrefsStore
import com.lightspark.sdk.LightsparkWalletClient
import com.lightspark.sdk.wrapWithLceFlow
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class WalletRepository @Inject constructor(
    private val prefsStore: DefaultPrefsStore,
    private val walletClient: LightsparkWalletClient,
) {
    fun getWalletDashboard() =
        wrapWithLceFlow { walletClient.getWalletDashboard() }.flowOn(Dispatchers.IO)

    val activeWalletId
        get() = walletClient.activeWalletId

    fun unlockWallet(password: String) =
        wrapWithLceFlow { walletClient.unlockActiveWallet(password) }

    fun setActiveWalletAndUnlock(nodeId: String, password: String) =
        wrapWithLceFlow {
            if (walletClient.unlockWallet(nodeId, password)) {
                prefsStore.setDefaultWalletNode(nodeId)
                true
            } else {
                false
            }
        }.flowOn(Dispatchers.IO)

    suspend fun setActiveWalletWithoutUnlocking(nodeId: String?) = withContext(Dispatchers.IO) {
        walletClient.setActiveWalletWithoutUnlocking(nodeId)
        prefsStore.setDefaultWalletNode(nodeId)
    }

    val isWalletUnlocked = walletClient.observeWalletUnlocked()
}
