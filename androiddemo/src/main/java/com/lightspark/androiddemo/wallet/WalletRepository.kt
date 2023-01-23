package com.lightspark.androiddemo.wallet

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.sdk.LightsparkWalletClient
import com.lightspark.sdk.wrapWithLceFlow

class WalletRepository(private val walletClient: LightsparkWalletClient = LightsparkClientProvider.walletClient) {
    fun getWalletDashboard() = wrapWithLceFlow { walletClient.getWalletDashboard() }
    fun unlockWallet(password: String) =
        wrapWithLceFlow { walletClient.unlockActiveWallet(password) }

    fun setActiveWalletAndUnlock(nodeId: String, password: String) =
        wrapWithLceFlow { walletClient.unlockWallet(nodeId, password) }

    val isWalletUnlocked = walletClient.observeWalletUnlocked()
}