package com.lightspark.androiddemo.accountdashboard

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.sdk.LightsparkClient
import com.lightspark.sdk.auth.AuthProvider
import com.lightspark.sdk.wrapWithLceFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class AccountDashboardRepository(
    private val lightsparkClient: LightsparkClient = LightsparkClientProvider.fullClient,
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

    val unlockedNodeIds = lightsparkClient.getUnlockedNodeIds()
}
