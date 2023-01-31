package com.lightspark.androiddemo.accountdashboard

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.sdk.LightsparkClient
import com.lightspark.sdk.wrapWithLceFlow

class AccountDashboardRepository(
    private val lightsparkClient: LightsparkClient = LightsparkClientProvider.fullClient,
) {
    suspend fun getDashboardData() = wrapWithLceFlow { lightsparkClient.getFullAccountDashboard() }

    suspend fun recoverNodeKey(nodeId: String, nodePassword: String) =
        lightsparkClient.recoverNodeSigningKey(nodeId, nodePassword)

    fun setAccountToken(tokenId: String, tokenSecret: String) {
        lightsparkClient.setAccountApiToken(tokenId, tokenSecret)
    }

    val unlockedNodeIds = lightsparkClient.getUnlockedNodeIds()
}
