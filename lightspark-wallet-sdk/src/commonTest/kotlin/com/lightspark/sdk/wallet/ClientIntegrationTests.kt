package com.lightspark.sdk.wallet

import com.lightspark.sdk.core.auth.StubAuthProvider
import com.lightspark.sdk.core.requester.ServerEnvironment
import com.lightspark.sdk.wallet.model.Account
import com.lightspark.sdk.wallet.model.BitcoinNetwork
import com.lightspark.sdk.wallet.model.LightsparkNode
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class ClientIntegrationTests {
    private val config = ClientConfig()
        .setServerUrl(ServerEnvironment.DEV.graphQLUrl)
        .setAuthProvider(
            StubAuthProvider(),
        )
        .setDefaultBitcoinNetwork(BitcoinNetwork.REGTEST)
    private val client = LightsparkCoroutinesWalletClient(config)

    @Test
    fun `get full account dashboard`() = runTest {
        val dashboard = client.getFullAccountDashboard()
        dashboard.shouldNotBeNull()
        val nodeConnection = dashboard.nodeConnection
        nodeConnection.shouldNotBeNull()
        nodeConnection.nodes.shouldNotBeEmpty()
    }

    private suspend fun getFirstNode(): LightsparkNode {
        val account = getCurrentAccount()
        val nodes = account.getNodesQuery().execute(client)
        nodes.shouldNotBeNull()
        nodes.entities.shouldNotBeEmpty()
        return nodes.entities.first()
    }

    private suspend fun getNodeId(): String {
        return getFirstNode().id
    }

    private suspend fun getCurrentAccount(): Account {
        val account = client.getCurrentWallet()
        account.shouldNotBeNull()
        return account
    }
}
