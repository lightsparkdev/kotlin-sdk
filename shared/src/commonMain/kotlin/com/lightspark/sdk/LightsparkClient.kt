package com.lightspark.sdk

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.lightspark.api.DashboardOverviewQuery
import com.lightspark.api.type.BitcoinNetwork
import com.lightspark.conf.BuildKonfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import saschpe.kase64.base64Encoded

private const val LIGHTSPARK_BETA_HEADER = "z2h0BBYxTA83cjW7fi8QwWtBPCzkQKiemcuhKY08LOo"

class LightsparkClient private constructor(
    tokenId: String,
    token: String,
    serverUrl: String = BuildKonfig.LIGHTSPARK_ENDPOINT,
) {
    private val cacheFactory: MemoryCacheFactory =
        MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)
    private val authToken = "$tokenId:$token".base64Encoded

    private val apolloClient = ApolloClient.Builder()
        .serverUrl(serverUrl)
        .normalizedCache(cacheFactory)
        .addHttpHeader("Authorization", "Basic $authToken")
        .addHttpHeader("Content-Type", "application/json")
        .addHttpHeader("X-Lightspark-Beta", LIGHTSPARK_BETA_HEADER)
        .build()

    suspend fun getDashboard(
        bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK),
        nodeId: String? = null,
        nodeIds: List<String>? = null,
        afterDate: Any? = null,
        beforeDate: Any? = null
    ): DashboardOverviewQuery.Current_account? {
        return apolloClient.query(
            DashboardOverviewQuery(
                bitcoinNetwork,
                Optional.presentIfNotNull(nodeId),
                Optional.presentIfNotNull(nodeIds),
                Optional.presentIfNotNull(afterDate),
                Optional.presentIfNotNull(beforeDate)
            )
        )
            .execute().dataAssertNoErrors.current_account
    }

    suspend fun getDashboardFlow(
        bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK),
        nodeId: String? = null,
        nodeIds: List<String>? = null,
        afterDate: Any? = null,
        beforeDate: Any? = null
    ): Flow<Result<DashboardOverviewQuery.Current_account>> = flow {
        val data =
            getDashboard(bitcoinNetwork, nodeId, nodeIds, afterDate, beforeDate) ?: throw Exception(
                "No data"
            )
        emit(data)
    }.asResult()

    class Builder {
        private var serverUrl: String = BuildKonfig.LIGHTSPARK_ENDPOINT
        private var tokenId = BuildKonfig.LIGHTSPARK_TOKEN_ID
        private var token = BuildKonfig.LIGHTSPARK_TOKEN

        fun serverUrl(serverUrl: String) = apply { this.serverUrl = serverUrl }

        fun build() = LightsparkClient(serverUrl, tokenId, token)
    }
}
