package com.lightspark.sdk

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.http.HttpHeader
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.lightspark.api.*
import com.lightspark.api.type.BitcoinNetwork
import com.lightspark.api.type.CurrencyAmountInput
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.conf.BuildKonfig
import com.lightspark.sdk.crypto.NodeKeyCache
import com.lightspark.sdk.crypto.SigningHttpInterceptor
import com.lightspark.sdk.crypto.SigningKeyDecryptor
import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.model.FeeEstimate
import com.lightspark.sdk.model.WalletDashboardData
import com.lightspark.sdk.model.toTransaction
import kotlinx.coroutines.flow.Flow
import saschpe.kase64.base64Encoded

private const val LIGHTSPARK_BETA_HEADER = "z2h0BBYxTA83cjW7fi8QwWtBPCzkQKiemcuhKY08LOo"

/**
 * Main entry point for the Lightspark SDK.
 *
 * Should be constructed using the Builder class.
 *
 * ```kotlin
 * // Initialize the client with account token info:
 * val lightsparkClient = LightsparkClient.Builder().apply {
 *     tokenId = "your-token-id"
 *     token = "your-secret-token"
 * }.build()
 *
 * // An example API call fetching the dashboard info for the active account:
 * val dashboard = lightsparkClient.getFullAccountDashboard()
 * ```
 *
 * Note: This client object keeps a local cache in-memory, so a single instance should be reused
 * throughout the lifetime of your app.
 */
class LightsparkClient internal constructor(
    tokenId: String,
    token: String,
    serverUrl: String = BuildKonfig.LIGHTSPARK_ENDPOINT,
    private val keyDecryptor: SigningKeyDecryptor = SigningKeyDecryptor(),
    private val nodeKeyCache: NodeKeyCache = NodeKeyCache(),
) {
    private val cacheFactory: MemoryCacheFactory =
        MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)
    private val authToken = "$tokenId:$token".base64Encoded
    private val defaultHeaders = listOf(
        HttpHeader("Authorization", "Basic $authToken"),
        HttpHeader("Content-Type", "application/json"),
        HttpHeader("X-Lightspark-Beta", LIGHTSPARK_BETA_HEADER)
    )

    internal val apolloClient = ApolloClient.Builder()
        .serverUrl(serverUrl)
        .normalizedCache(cacheFactory)
        .httpHeaders(defaultHeaders)
        .addHttpInterceptor(SigningHttpInterceptor(nodeKeyCache))
        .build()

    /**
     * Get the dashboard overview for the active account (for the auth token that initialized this client).
     *
     * @param bitcoinNetwork The bitcoin network to use for the dashboard data. Defaults to the network set in the
     *      gradle project properties.
     * @param afterDate Optional date to filter the dashboard data to only include transactions after this date.
     * @param beforeDate Optional date to filter the dashboard data to only include transactions before this date.
     * @param nodeId Optional node ID to filter the dashboard data to a single node.
     * @param nodeIds Optional list of node IDs to filter the dashboard data to a list of nodes.
     *
     * @return The dashboard overview for the active account, including node and balance details.
     */
    suspend fun getFullAccountDashboard(
        bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK),
        afterDate: Any? = null,
        beforeDate: Any? = null,
        nodeId: String? = null,
        nodeIds: List<String>? = null
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

    /**
     * Get the dashboard overview for a single node as a lightning wallet. Includes balance info and
     * the most recent transactions.
     *
     * @param nodeId The ID of the node for which to fetch the dashboard data.
     * @param numTransactions The max number of recent transactions to fetch. Defaults to 20.
     * @param bitcoinNetwork The bitcoin network to use for the dashboard data. Defaults to the network set in the
     *      gradle project properties
     * @return The dashboard overview for the node, including balance and recent transactions.
     */
    suspend fun getSingleNodeDashboard(
        nodeId: String,
        numTransactions: Int = 20,
        bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK),
    ): WalletDashboardData? {
        val accountResponse = apolloClient.query(
            SingeNodeDashboardQuery(
                bitcoinNetwork,
                nodeId,
                Optional.presentIfNotNull(numTransactions)
            )
        )
            .execute().dataAssertNoErrors.current_account ?: return null
        return WalletDashboardData(
            accountName = accountResponse.name ?: "",
            balance = accountResponse.blockchain_balance?.available_balance?.let {
                CurrencyAmount(it.value, it.unit)
            } ?: CurrencyAmount(0, CurrencyUnit.SATOSHI),
            recentTransactions = accountResponse.recent_transactions.edges.map {
                it.entity.transactionDetails.toTransaction()
            }
        )
    }

    /**
     * Creates a lightning invoice for the given node.
     *
     * @param nodeId The ID of the node for which to create the invoice.
     * @param amount The amount of the invoice in a specified currency unit.
     * @param memo Optional memo to include in the invoice.
     */
    suspend fun createInvoice(
        nodeId: String,
        amount: CurrencyAmount,
        memo: String? = null,
    ): CreateInvoiceMutation.Invoice {
        return apolloClient.mutation(
            CreateInvoiceMutation(
                nodeId,
                CurrencyAmountInput(amount.amount, amount.unit),
                Optional.presentIfNotNull(memo)
            )
        )
            .execute().dataAssertNoErrors.create_invoice.invoice
    }

    /**
     * Pay a lightning invoice for the given node.
     *
     * Note: This call will fail if the node sending the payment is not unlocked yet via the [recoverNodeSigningKey]
     * function. You must successfully unlock the node with its password before calling this function.
     *
     * @param nodeId The ID of the node which will pay the invoice.
     * @param encodedInvoice An encoded string representation of the invoice to pay.
     * @param timeoutSecs The number of seconds to wait for the payment to complete. Defaults to 60.
     * @param amount The amount to pay in a specified currency unit. Defaults to the full amount of the invoice.
     * @param maxFees The maximum fees to pay in a specified currency unit.
     * @return The payment details.
     */
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

    /**
     * Decode a lightning invoice to get its details included payment amount, destination, etc.
     *
     * @param encodedInvoice An encoded string representation of the invoice to decode.
     * @return The decoded invoice details.
     */
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

    /**
     * Get the fee estimate for a payment.
     *
     * @param bitcoinNetwork The bitcoin network to use for the fee estimate. Defaults to the network set in the gradle
     *      project properties.
     * @return The fee estimate including a fast and minimum fee as [CurrencyAmount]s
     */
    suspend fun getFeeEstimate(
        bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)
    ): FeeEstimate {
        return apolloClient.query(FeeEstimateQuery(bitcoinNetwork))
            .execute().dataAssertNoErrors.fee_estimate.let {
                FeeEstimate(
                    CurrencyAmount(
                        it.fee_fast.currency_amount_value,
                        it.fee_fast.currency_amount_unit
                    ),
                    CurrencyAmount(
                        it.fee_min.currency_amount_value,
                        it.fee_min.currency_amount_unit
                    )
                )
            }
    }

    /**
     * @return A [Flow] that emits the set of node IDs that have been unlocked via the [recoverNodeSigningKey] function.
     */
    fun getUnlockedNodeIds(): Flow<Set<String>> = nodeKeyCache.observeCachedNodeIds()

    // TODO(Jeremy): Think through key management a bit more as it pertains to the SDK and its responsibilities.
    /**
     * Unlocks a node for use with the SDK for the current application session. This function must be called before any other functions that require node signing keys, including [payInvoice]...
     *
     * @param nodeId The ID of the node to unlock.
     * @param nodePassword The password for the node.
     * @return True if the node was successfully unlocked, false otherwise.
     */
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

    /**
     * The Builder class for [LightsparkClient] and the main entry point for the SDK.
     *
     * ```kotlin
     * // Initialize the client with account token info:
     * val lightsparkClient = LightsparkClient.Builder().apply {
     *     tokenId = "your-token-id"
     *     token = "your-secret-token"
     * }.build()
     * ```
     *
     * @param serverUrl The URL of the Lightspark server to connect to. Defaults to a value set in gradle project properties.
     * @param tokenId The token ID to use for authentication. Defaults to a value set in gradle project properties. You can find
     *      this value in the Lightspark dashboard.
     * @param token The token to use for authentication. Defaults to a value set in gradle project properties. You can find this
     *      value in the Lightspark dashboard.
     * @return A [LightsparkClient] instance.
     */
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
