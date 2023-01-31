package com.lightspark.androiddemo.wallet

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.androiddemo.auth.CredentialsStore
import com.lightspark.sdk.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class WalletRepository(
    private val walletClient: LightsparkWalletClient = LightsparkClientProvider.walletClient,
    credentialsStore: CredentialsStore = CredentialsStore.instance
) {
    private val hasCredentials = credentialsStore.getAccountTokenFlow().map { it != null }
    fun getWalletDashboard() = combine(
        hasCredentials,
        wrapWithLceFlow { walletClient.getWalletDashboard() }
    ) { hasCredentials, dashboard ->
        if (!hasCredentials) {
            Lce.Error(
                LightsparkException(
                    "No credentials stored",
                    LightsparkErrorCode.NO_CREDENTIALS
                )
            )
        } else {
            dashboard
        }
    }

    fun unlockWallet(password: String) =
        wrapWithLceFlow { walletClient.unlockActiveWallet(password) }

    fun setActiveWalletAndUnlock(nodeId: String, password: String) =
        wrapWithLceFlow { walletClient.unlockWallet(nodeId, password) }

    val isWalletUnlocked = walletClient.observeWalletUnlocked()
}