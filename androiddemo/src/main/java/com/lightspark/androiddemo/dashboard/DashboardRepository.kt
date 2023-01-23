package com.lightspark.androiddemo.dashboard

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.sdk.LightsparkClient
import com.lightspark.sdk.wrapWithLceFlow

class DashboardRepository(private val lightsparkClient: LightsparkClient = LightsparkClientProvider.fullClient) {
    suspend fun getDashboardData() = wrapWithLceFlow { lightsparkClient.getFullAccountDashboard() }

    suspend fun getWalletDashboard(nodeId: String) =
        wrapWithLceFlow { lightsparkClient.getWalletDashboard(nodeId) }

    suspend fun recoverNodeKey(nodeId: String, nodePassword: String) =
        lightsparkClient.recoverNodeSigningKey(nodeId, nodePassword)

    val unlockedNodeIds = lightsparkClient.getUnlockedNodeIds()
}