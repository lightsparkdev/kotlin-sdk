package com.lightspark.androiddemo.wallet

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.sdk.LightsparkWalletClient
import com.lightspark.sdk.wrapWithLceFlow

class WalletRepository(private val walletClient: LightsparkWalletClient = LightsparkClientProvider.walletClient) {
    suspend fun getWalletDashboard() = wrapWithLceFlow { walletClient.getWalletDashboard() }
    suspend fun unlockWallet(password: String) =
        wrapWithLceFlow { walletClient.unlockActiveWallet(password) }

    val isWalletUnlocked = walletClient.observeWalletUnlocked()
}