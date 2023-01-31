package com.lightspark.androiddemo.wallet

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.androiddemo.auth.CredentialsStore
import com.lightspark.sdk.*
import kotlinx.coroutines.flow.combine

class WalletRepository(
    private val walletClient: LightsparkWalletClient = LightsparkClientProvider.walletClient,
    private val credentialsStore: CredentialsStore = CredentialsStore.instance
) {
    fun getWalletDashboard() = combine(
        credentialsStore.getAccountTokenFlow(),
        wrapWithLceFlow { walletClient.getWalletDashboard() }
    ) { credentials, dashboard ->
        if (credentials == null) {
            Lce.Error(
                LightsparkException(
                    "No credentials stored",
                    LightsparkErrorCode.NO_CREDENTIALS
                )
            )
        } else if (credentials.defaultWalletNodeId == null) {
            Lce.Error(
                LightsparkException(
                    "No wallet id stored",
                    LightsparkErrorCode.MISSING_WALLET_ID
                )
            )
        } else {
            dashboard
        }
    }

    fun unlockWallet(password: String) =
        wrapWithLceFlow { walletClient.unlockActiveWallet(password) }

    fun setActiveWalletAndUnlock(nodeId: String, password: String) =
        wrapWithLceFlow {
            if (walletClient.unlockWallet(nodeId, password)) {
                credentialsStore.getAccountTokenSync()?.let {
                    credentialsStore.setAccountData(it.tokenId, it.tokenSecret, nodeId)
                }
                true
            } else {
                false
            }
        }

    suspend fun setActiveWalletWithoutUnlocking(nodeId: String) {
        walletClient.setActiveWalletWithoutUnlocking(nodeId)
        credentialsStore.getAccountTokenSync()?.let {
            credentialsStore.setAccountData(it.tokenId, it.tokenSecret, nodeId)
        }
    }

    val isWalletUnlocked = walletClient.observeWalletUnlocked()
}