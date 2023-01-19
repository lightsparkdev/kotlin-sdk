package com.lightspark.sdk

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.lightspark.api.CreateInvoiceMutation
import com.lightspark.api.DashboardOverviewQuery
import com.lightspark.api.DecodedPaymentRequestQuery
import com.lightspark.api.FeeEstimateQuery
import com.lightspark.api.type.BitcoinNetwork
import com.lightspark.api.type.CurrencyAmountInput
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

    suspend fun createInvoice(
        nodeId: String,
        amount: CurrencyAmountInput,
        memo: String? = null,
    ): CreateInvoiceMutation.Invoice {
        return apolloClient.mutation(
            CreateInvoiceMutation(
                nodeId,
                amount,
                Optional.presentIfNotNull(memo)
            )
        )
            .execute().dataAssertNoErrors.create_invoice.invoice
    }

    suspend fun decodeInvoice(
        encodedInvoice: String,
    ): DecodedPaymentRequestQuery.OnInvoiceData? {
        return apolloClient.query(
            DecodedPaymentRequestQuery(
                encodedInvoice
            )
        )
            .execute().dataAssertNoErrors.decoded_payment_request.onInvoiceData
    }

    suspend fun getFeeEstimate(
        bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)
    ): FeeEstimateQuery.Fee_estimate {
        return apolloClient.query(FeeEstimateQuery(bitcoinNetwork))
            .execute().dataAssertNoErrors.fee_estimate
    }

    suspend fun <T> wrapFlowableResult(query: suspend () -> T?): Flow<Result<T>> = flow {
        val data = query() ?: throw Exception("No data")
        emit(data)
    }.asResult()

    class Builder {
        private var serverUrl: String = BuildKonfig.LIGHTSPARK_ENDPOINT
        private var tokenId = BuildKonfig.LIGHTSPARK_TOKEN_ID
        private var token = BuildKonfig.LIGHTSPARK_TOKEN

        fun serverUrl(serverUrl: String) = apply { this.serverUrl = serverUrl }
        fun tokenId(tokenId: String) = apply { this.tokenId = tokenId }
        fun token(token: String) = apply { this.token = token }

        fun build() = LightsparkClient(tokenId, token, serverUrl)
    }
}
