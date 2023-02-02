package com.lightspark.androiddemo.accountdashboard

import com.lightspark.api.type.BitcoinNetwork
import com.lightspark.sdk.LightsparkClient
import com.lightspark.sdk.auth.AuthProvider
import com.lightspark.sdk.model.ServerEnvironment
import com.lightspark.sdk.wrapWithLceFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AccountDashboardRepository @Inject constructor(
    private val lightsparkClient: LightsparkClient,
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
