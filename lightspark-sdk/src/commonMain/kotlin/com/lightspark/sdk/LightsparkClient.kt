package com.lightspark.sdk

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.http.HttpHeader
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.lightspark.api.*
import com.lightspark.api.type.BitcoinNetwork
import com.lightspark.api.type.CurrencyAmountInput
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.conf.BuildKonfig
import com.lightspark.sdk.auth.*
import com.lightspark.sdk.crypto.NodeKeyCache
import com.lightspark.sdk.crypto.SigningHttpInterceptor
import com.lightspark.sdk.crypto.SigningKeyDecryptor
import com.lightspark.sdk.model.*
import kotlinx.coroutines.flow.Flow
import saschpe.kase64.base64DecodedBytes

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
    private var authProvider: AuthProvider,
    private var serverUrl: String = BuildKonfig.LIGHTSPARK_ENDPOINT,
    private var defaultBitcoinNetwork: BitcoinNetwork = BitcoinNetwork.REGTEST,
    private val keyDecryptor: SigningKeyDecryptor = SigningKeyDecryptor(),
    internal val nodeKeyCache: NodeKeyCache = NodeKeyCache(),
) {
    private val cacheFactory: MemoryCacheFactory =
        MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)
    private val defaultHeaders = listOf(
        HttpHeader("Content-Type", "application/json"),
        HttpHeader(BETA_HEADER_KEY, BETA_HEADER_VALUE)
    )

    internal var apolloClient = ApolloClient.Builder()
        .serverUrl(serverUrl)
        .normalizedCache(cacheFactory)
        .httpHeaders(defaultHeaders)
        .addHttpInterceptor(SigningHttpInterceptor(nodeKeyCache))
        .build()

    private suspend fun <T : Operation.Data> ApolloCall<T>.addingHeaders(
        extraHeaders: List<HttpHeader> = emptyList()
    ) = httpHeaders(
        authProvider.getCredentialHeaders().map { HttpHeader(it.key, it.value) }
            .plus(defaultHeaders)
            .plus(extraHeaders)
    )

    /**
     * Override the auth token provider for this client to provide custom headers on all API calls.
     */
    fun setAuthProvider(authProvider: AuthProvider) {
        this.nodeKeyCache.clear()
        this.authProvider = authProvider
    }

    /**
     * Set the account API token info for this client, thereby overriding the auth token provider to use
     * account-based authentication.
     */
    fun setAccountApiToken(tokenId: String, tokenSecret: String) {
        setAuthProvider(AccountApiTokenAuthProvider(tokenId, tokenSecret))
    }

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
        bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork,
        afterDate: Any? = null,
        beforeDate: Any? = null,
        nodeId: String? = null,
        nodeIds: List<String>? = null
    ): DashboardOverviewQuery.Current_account? {
        requireValidAuth()
        return apolloClient.query(
            DashboardOverviewQuery(
                bitcoinNetwork,
                Optional.presentIfNotNull(nodeId),
                Optional.presentIfNotNull(nodeIds),
                Optional.presentIfNotNull(afterDate),
                Optional.presentIfNotNull(beforeDate)
            )
        )
            .addingHeaders()
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
        bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork,
    ): WalletDashboardData? {
        requireValidAuth()
        val accountResponse = apolloClient.query(
            SingeNodeDashboardQuery(
                bitcoinNetwork,
                nodeId,
                Optional.presentIfNotNull(numTransactions)
            )
        )
            .addingHeaders()
            .execute().dataAssertNoErrors.current_account ?: return null
        return WalletDashboardData(
            accountName = accountResponse.name ?: "",
            nodeDisplayName = accountResponse.dashboard_overview_nodes.edges.firstOrNull()?.entity?.display_name
                ?: "",
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
        requireValidAuth()
        return apolloClient.mutation(
            CreateInvoiceMutation(
                nodeId,
                CurrencyAmountInput(amount.amount, amount.unit),
                Optional.presentIfNotNull(memo)
            )
        )
            .addingHeaders()
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
        requireValidAuth()
        return apolloClient.mutation(
            PayInvoiceMutation(
                nodeId,
                encodedInvoice,
                timeoutSecs,
                Optional.presentIfNotNull(amount),
                Optional.presentIfNotNull(maxFees)
            )
        )
            .addingHeaders(listOf(HttpHeader("X-Lightspark-node-id", nodeId)))
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
            .addingHeaders()
            .execute().dataAssertNoErrors.decoded_payment_request.onInvoiceData
    }

    /**
     * Get the fee estimate for a payment.
     *
     * @param bitcoinNetwork The bitcoin network to use for the fee estimate. Defaults to the network set in the gradle
     *      project properties.
     * @return The fee estimate including a fast and minimum fee as [CurrencyAmount]s
     */
    suspend fun getFeeEstimate(bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork): FeeEstimate {
        requireValidAuth()
        return apolloClient.query(FeeEstimateQuery(bitcoinNetwork))
            .addingHeaders()
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
        requireValidAuth()
        val response = apolloClient.query(RecoverNodeSigningKeyQuery(nodeId))
            .addingHeaders()
            .execute().dataAssertNoErrors.entity?.onLightsparkNode?.encrypted_signing_private_key
            ?: return false
        try {
            val unencryptedKey =
                keyDecryptor.decryptKey(response.cipher, nodePassword, response.encrypted_value)
            nodeKeyCache[nodeId] = unencryptedKey.decodeToString().base64DecodedBytes
        } catch (e: Exception) {
            return false
        }
        return true
    }

    internal fun requireValidAuth() {
        if (!authProvider.isAccountAuthorized()) {
            throw LightsparkAuthenticationException()
        }
    }

    fun setServerEnvironment(environment: ServerEnvironment) {
        serverUrl = environment.graphQLUrl
        apolloClient = ApolloClient.Builder()
            .serverUrl(serverUrl)
            .normalizedCache(cacheFactory)
            .httpHeaders(defaultHeaders)
            .addHttpInterceptor(SigningHttpInterceptor(nodeKeyCache))
            .build()
        authProvider = StubAuthProvider()
    }

    fun setBitcoinNetwork(network: BitcoinNetwork) {
        defaultBitcoinNetwork = network
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
        private var defaultBitcoinNetwork: BitcoinNetwork =
            BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)
                .takeIf { it != BitcoinNetwork.UNKNOWN__ } ?: BitcoinNetwork.REGTEST
        private var tokenId: String? = null
        private var token: String? = null
        private var authProvider: AuthProvider? = null

        fun serverUrl(serverUrl: String) = apply { this.serverUrl = serverUrl }
        fun bitcoinNetwork(bitcoinNetwork: BitcoinNetwork) =
            apply { this.defaultBitcoinNetwork = bitcoinNetwork }

        fun tokenId(tokenId: String) = apply { this.tokenId = tokenId }
        fun token(token: String) = apply { this.token = token }
        fun authProvider(authProvider: AuthProvider) =
            apply { this.authProvider = authProvider }

        fun build(): LightsparkClient {
            val authProvider = this.authProvider ?: if (tokenId != null && token != null) {
                AccountApiTokenAuthProvider(tokenId!!, token!!)
            } else {
                StubAuthProvider()
            }
            return LightsparkClient(
                authProvider,
                serverUrl = serverUrl,
                defaultBitcoinNetwork = defaultBitcoinNetwork
            )
        }
    }
}
