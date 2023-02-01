package com.lightspark.androiddemo.wallet

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.androiddemo.auth.CredentialsStore
import com.lightspark.sdk.LightsparkWalletClient
import com.lightspark.sdk.wrapWithLceFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WalletRepository(
    private val walletClient: LightsparkWalletClient = LightsparkClientProvider.walletClient,
    private val credentialsStore: CredentialsStore = CredentialsStore.instance
) {
    fun getWalletDashboard() = wrapWithLceFlow { walletClient.getWalletDashboard() }

    fun unlockWallet(password: String) =
        wrapWithLceFlow { walletClient.unlockActiveWallet(password) }

    fun setActiveWalletAndUnlock(nodeId: String, password: String) =
        wrapWithLceFlow {
            if (walletClient.unlockWallet(nodeId, password)) {
                credentialsStore.getAccountTokenSync()?.let {
                    credentialsStore.setAccountData(it.tokenId, it.tokenSecret)
                }
                true
            } else {
                false
            }
        }

    suspend fun setActiveWalletWithoutUnlocking(nodeId: String) = withContext(Dispatchers.IO) {
        walletClient.setActiveWalletWithoutUnlocking(nodeId)
        credentialsStore.getAccountTokenSync()?.let {
            credentialsStore.setAccountData(it.tokenId, it.tokenSecret)
        }
    }

    val isWalletUnlocked = walletClient.observeWalletUnlocked()
}