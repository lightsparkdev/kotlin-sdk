package com.lightspark.sdk

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.http.HttpHeader
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.lightspark.api.*
import com.lightspark.api.type.BitcoinNetwork
import com.lightspark.api.type.CurrencyAmountInput
import com.lightspark.conf.BuildKonfig
import com.lightspark.sdk.crypto.NodeKeyCache
import com.lightspark.sdk.crypto.SigningHttpInterceptor
import com.lightspark.sdk.crypto.SigningKeyDecryptor
import com.lightspark.sdk.crypto.signPayload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import saschpe.kase64.base64Encoded

private const val LIGHTSPARK_BETA_HEADER = "z2h0BBYxTA83cjW7fi8QwWtBPCzkQKiemcuhKY08LOo"

class LightsparkClient private constructor(
    tokenId: String,
    token: String,
    serverUrl: String = BuildKonfig.LIGHTSPARK_ENDPOINT,
    private val keyDecryptor: SigningKeyDecryptor = SigningKeyDecryptor(),
    private val nodeKeyCache: NodeKeyCache = NodeKeyCache()
) {
    private val cacheFactory: MemoryCacheFactory =
        MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)
    private val authToken = "$tokenId:$token".base64Encoded
    private val defaultHeaders = listOf(
        HttpHeader("Authorization", "Basic $authToken"),
        HttpHeader("Content-Type", "application/json"),
        HttpHeader("X-Lightspark-Beta", LIGHTSPARK_BETA_HEADER)
    )

    private val apolloClient = ApolloClient.Builder()
        .serverUrl(serverUrl)
        .normalizedCache(cacheFactory)
        .httpHeaders(defaultHeaders)
        .addHttpInterceptor(SigningHttpInterceptor(nodeKeyCache))
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

    suspend fun payInvoice(
        nodeId: String,
        encodedInvoice: String,
        timeoutSecs: Int = 60,
        amount: CurrencyAmountInput? = null,
        maxFees: CurrencyAmountInput? = null,
    ): PayInvoiceMutation.Payment {
        return apolloClient.mutation(
            PayInvoiceMutation(
                nodeId,
                encodedInvoice,
                timeoutSecs,
                Optional.presentIfNotNull(amount),
                Optional.presentIfNotNull(maxFees)
            )
        )
            .httpHeaders(defaultHeaders + HttpHeader("X-Lightspark-node-id", nodeId))
            .execute().dataAssertNoErrors.pay_invoice.payment
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

    fun getUnlockedNodeIds() = nodeKeyCache.observeCachedNodeIds()

    // TODO(Jeremy): Think through key management a bit more as it pertains to the SDK and its responsibilities.
    suspend fun recoverNodeSigningKey(
        nodeId: String,
        nodePassword: String,
    ): Boolean {
        val response = apolloClient.query(RecoverNodeSigningKeyQuery(nodeId))
            .execute().dataAssertNoErrors.entity?.onLightsparkNode?.encrypted_signing_private_key
            ?: return false
        try {
            val unencryptedKey =
                keyDecryptor.decryptKey(response.cipher, nodePassword, response.encrypted_value)
            nodeKeyCache[nodeId] = unencryptedKey
        } catch (e: Exception) {
            return false
        }
        return true
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
