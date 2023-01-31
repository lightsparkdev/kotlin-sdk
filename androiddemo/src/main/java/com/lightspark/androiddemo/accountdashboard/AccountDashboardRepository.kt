package com.lightspark.androiddemo.accountdashboard

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.androiddemo.auth.CredentialsStore
import com.lightspark.sdk.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class AccountDashboardRepository(
    private val lightsparkClient: LightsparkClient = LightsparkClientProvider.fullClient,
    credentialsStore: CredentialsStore = CredentialsStore.instance
) {
    private val hasCredentials = credentialsStore.getAccountTokenFlow().map { it != null }
    suspend fun getDashboardData() = combine(
        hasCredentials,
        wrapWithLceFlow { lightsparkClient.getFullAccountDashboard() }
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

    suspend fun recoverNodeKey(nodeId: String, nodePassword: String) =
        lightsparkClient.recoverNodeSigningKey(nodeId, nodePassword)

    fun setAccountToken(tokenId: String, tokenSecret: String) {
        lightsparkClient.setAccountApiToken(tokenId, tokenSecret)
    }

    val unlockedNodeIds = lightsparkClient.getUnlockedNodeIds()
}
