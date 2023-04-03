package com.lightspark.sdk

import com.lightspark.sdk.model.BitcoinNetwork
import com.lightspark.sdk.requester.ServerEnvironment
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LightsparkClientTests {
    private val client = LightsparkClient.Builder()
        .serverUrl(ServerEnvironment.DEV.graphQLUrl)
        .tokenId("018733dfb1bf4f890000b4d3d36810d7")
        .token("7vfWf4PdBHBeODJaxOQVSxnJ4ywXPnuXUCkB6Bw5Jdw")
        .bitcoinNetwork(BitcoinNetwork.REGTEST).build()

    @Test
    fun `get full account dashboard`() = runTest {
        val dashboard = client.getFullAccountDashboard()
        dashboard.shouldNotBeNull()
        val nodeConnection = dashboard.nodeConnection
        nodeConnection.shouldNotBeNull()
        nodeConnection.nodes.shouldNotBeEmpty()
    }
}
