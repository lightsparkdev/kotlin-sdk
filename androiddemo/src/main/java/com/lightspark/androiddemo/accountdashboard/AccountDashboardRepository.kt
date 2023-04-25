package com.lightspark.androiddemo.accountdashboard

import com.lightspark.sdk.core.auth.AuthProvider
import com.lightspark.sdk.core.requester.ServerEnvironment
import com.lightspark.sdk.core.wrapWithLceFlow
import com.lightspark.sdk.server.LightsparkCoroutinesClient
import com.lightspark.sdk.server.model.BitcoinNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AccountDashboardRepository @Inject constructor(
    private val lightsparkClient: LightsparkCoroutinesClient,
) {
    suspend fun getDashboardData() =
        wrapWithLceFlow { lightsparkClient.getFullAccountDashboard() }.flowOn(Dispatchers.IO)

    suspend fun recoverNodeKey(nodeId: String, nodePassword: String) =
        lightsparkClient.recoverNodeSigningKey(nodeId, nodePassword)

    fun setAccountToken(tokenId: String, tokenSecret: String) {
        lightsparkClient.setAccountApiToken(tokenId, tokenSecret)
    }

    fun setAuthProvider(provider: AuthProvider) {
        lightsparkClient.setAuthProvider(provider)
    }

    fun setBitcoinNetwork(bitcoinNetwork: BitcoinNetwork) {
        lightsparkClient.setBitcoinNetwork(bitcoinNetwork)
    }

    fun setServerEnvironment(serverEnvironment: ServerEnvironment, invalidateAuth: Boolean) {
        lightsparkClient.setServerEnvironment(serverEnvironment, invalidateAuth)
    }

    val unlockedNodeIds = lightsparkClient.getUnlockedNodeIds()
}
