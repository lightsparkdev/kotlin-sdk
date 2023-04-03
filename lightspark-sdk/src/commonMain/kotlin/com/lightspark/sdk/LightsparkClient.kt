package com.lightspark.sdk

import com.lightspark.sdk.auth.*
import com.lightspark.sdk.crypto.NodeKeyCache
import com.lightspark.sdk.crypto.SigningKeyDecryptor
import com.lightspark.sdk.graphql.*
import com.lightspark.sdk.model.*
import com.lightspark.sdk.requester.Query
import com.lightspark.sdk.requester.Requester
import com.lightspark.sdk.requester.ServerEnvironment
import com.lightspark.sdk.util.serializerFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.*
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
    private var serverUrl: String = ServerEnvironment.PROD.graphQLUrl,
    private var defaultBitcoinNetwork: BitcoinNetwork = BitcoinNetwork.REGTEST,
    private val keyDecryptor: SigningKeyDecryptor = SigningKeyDecryptor(),
    internal val nodeKeyCache: NodeKeyCache = NodeKeyCache(),
) {
    internal var requester = Requester(nodeKeyCache, authProvider, serverUrl)

    /**
     * Override the auth token provider for this client to provide custom headers on all API calls.
     */
    fun setAuthProvider(authProvider: AuthProvider) {
        nodeKeyCache.clear()
        this.authProvider = authProvider
        requester = Requester(nodeKeyCache, authProvider, serverUrl)
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
     * @param nodeIds Optional list of node IDs to filter the dashboard data to a list of nodes.
     *
     * @return The dashboard overview for the active account, including node and balance details.
     */
    suspend fun getFullAccountDashboard(
        bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork,
        nodeIds: List<String>? = null,
    ): AccountDashboard? {
        requireValidAuth()
        return executeRawQuery(
            Query(
                AccountDashboardQuery,
                {
                    add("network", bitcoinNetwork)
                    add("nodeIds", nodeIds)
                },
            ) {
                val account =
                    requireNotNull(it["current_account"]) { "No account found in response" }
                serializerFormat.decodeFromJsonElement(account)
            },
        )
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
    ): WalletDashboard? {
        requireValidAuth()
        return executeRawQuery(
            Query(
                SingleNodeDashboardQuery,
                {
                    add("network", bitcoinNetwork)
                    add("nodeId", nodeId)
                    add("numTransactions", numTransactions)
                },
            ) { jsonObj ->
                val accountJson =
                    requireNotNull(jsonObj["current_account"]?.jsonObject) { "No account found in response" }

                val nodeJson =
                    accountJson["dashboard_overview_nodes"]?.jsonObject?.get("entities")
                        ?.jsonArray?.firstOrNull()?.jsonObject
                        ?: return@Query null
                val transactionsJson =
                    accountJson["recent_transactions"]?.jsonObject?.get("entities")
                        ?.jsonArray ?: emptyList()
                transactionsJson.forEach {
                    val transaction = it.jsonObject
                    val transactionType = transaction["type"]?.jsonPrimitive?.content
                    if (transactionType == null) {
                        print("Transaction type is null")
                        print("Transaction:  $transaction")
                    }
                }

                return@Query WalletDashboard(
                    accountJson["id"]!!.jsonPrimitive.content,
                    nodeJson["display_name"]!!.jsonPrimitive.content,
                    nodeJson["purpose"]?.jsonPrimitive?.content?.let {
                        LightsparkNodePurpose.valueOf(it)
                    },
                    nodeJson["color"]?.jsonPrimitive?.content,
                    nodeJson["public_key"]?.jsonPrimitive?.content,
                    nodeJson["status"]?.jsonPrimitive?.content?.let {
                        LightsparkNodeStatus.valueOf(it)
                    },
                    serializerFormat.decodeFromJsonElement<NodeToAddressesConnection>(nodeJson["addresses"]!!).entities,
                    accountJson["local_balance"]?.let { serializerFormat.decodeFromJsonElement(it) },
                    accountJson["remote_balance"]?.let { serializerFormat.decodeFromJsonElement(it) },
                    accountJson["blockchain_balance"]?.let {
                        serializerFormat.decodeFromJsonElement(
                            it,
                        )
                    },
                    serializerFormat.decodeFromJsonElement(accountJson["recent_transactions"]!!),
                )
            },
        )
    }

    /**
     * Creates a lightning invoice for the given node.
     *
     * @param nodeId The ID of the node for which to create the invoice.
     * @param amountMsats The amount of the invoice in milli-satoshis.
     * @param memo Optional memo to include in the invoice.
     * @param type The type of invoice to create. Defaults to [InvoiceType.STANDARD].
     */
    suspend fun createInvoice(
        nodeId: String,
        amountMsats: Long,
        memo: String? = null,
        type: InvoiceType = InvoiceType.STANDARD,
    ): InvoiceData {
        requireValidAuth()
        return executeRawQuery(
            Query(
                CreateInvoiceMutation,
                {
                    add("nodeId", nodeId)
                    add("amountMsats", amountMsats)
                    add("memo", memo)
                    add("type", type)
                },
            ) {
                val invoiceJson =
                    requireNotNull(
                        it["create_invoice"]?.jsonObject?.get("invoice")?.jsonObject?.get("data"),
                    ) { "No invoice found in response" }
                serializerFormat.decodeFromJsonElement(invoiceJson)
            },
        )!!
    }

    /**
     * Pay a lightning invoice for the given node.
     *
     * Note: This call will fail if the node sending the payment is not unlocked yet via the [recoverNodeSigningKey]
     * function. You must successfully unlock the node with its password before calling this function.
     *
     * @param nodeId The ID of the node which will pay the invoice.
     * @param encodedInvoice An encoded string representation of the invoice to pay.
     * @param maxFeesMsats The maximum fees to pay in milli-satoshis. You must pass a value.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param amountMsats The amount to pay in milli-satoshis. Defaults to the full amount of the invoice.
     * @param timeoutSecs The number of seconds to wait for the payment to complete. Defaults to 60.
     * @return The payment details.
     */
    suspend fun payInvoice(
        nodeId: String,
        encodedInvoice: String,
        maxFeesMsats: Long,
        amountMsats: Long? = null,
        timeoutSecs: Int = 60,
    ): OutgoingPayment {
        requireValidAuth()
        return executeRawQuery(
            Query(
                PayInvoiceMutation,
                {
                    add("node_id", nodeId)
                    add("encoded_invoice", encodedInvoice)
                    add("timeout_secs", timeoutSecs)
                    add("amount_msats", amountMsats)
                    add("maximum_fees_msats", maxFeesMsats)
                },
                signingNodeId = nodeId,
            ) {
                val paymentJson =
                    requireNotNull(it["pay_invoice"]?.jsonObject?.get("payment")) { "No payment found in response" }
                serializerFormat.decodeFromJsonElement(paymentJson)
            },
        )!!
    }

    /**
     * Decode a lightning invoice to get its details included payment amount, destination, etc.
     *
     * @param encodedInvoice An encoded string representation of the invoice to decode.
     * @return The decoded invoice details.
     */
    suspend fun decodeInvoice(
        encodedInvoice: String,
    ): InvoiceData? {
        return executeRawQuery(
            Query(
                DecodeInvoiceQuery,
                { add("encoded_payment_request", encodedInvoice) },
            ) {
                val invoiceJson =
                    requireNotNull(it["decoded_payment_request"]) { "No invoice found in response" }
                serializerFormat.decodeFromJsonElement(invoiceJson)
            },
        )
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
        return executeRawQuery(
            Query(
                FeeEstimateQuery,
                { add("bitcoin_network", bitcoinNetwork) },
            ) {
                val feeEstimateJson =
                    requireNotNull(it["fee_estimate"]) { "No fee estimate found in response" }
                serializerFormat.decodeFromJsonElement(feeEstimateJson)
            },
        )!!
    }

    /**
     * @return A [Flow] that emits the set of node IDs that have been unlocked via the [recoverNodeSigningKey] function.
     */
    fun getUnlockedNodeIds(): Flow<Set<String>> = nodeKeyCache.observeCachedNodeIds()

    // TODO: Think through key management a bit more as it pertains to the SDK and its responsibilities.
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
        val response =
            requester.makeRawRequest(
                RecoverNodeSigningKeyQuery,
                buildJsonObject { put("nodeId", nodeId) },
            )
        val keyJson =
            response?.get("entity")?.jsonObject?.get("encrypted_signing_private_key")?.jsonObject
                ?: return false
        try {
            val unencryptedKey =
                keyDecryptor.decryptKey(
                    keyJson["cipher"]!!.jsonPrimitive.content,
                    nodePassword,
                    keyJson["encrypted_value"]!!.jsonPrimitive.content,
                )
            nodeKeyCache[nodeId] = unencryptedKey.decodeToString().base64DecodedBytes
        } catch (e: Exception) {
            return false
        }
        return true
    }

    /**
     * Create a new API token for the current account.
     *
     * @param name The name of the token.
     * @param permissions The permissions to grant the token. Defaults to Permission.ALL.
     * @return The newly created token.
     */
    suspend fun createApiToken(
        name: String,
        permissions: List<Permission> = listOf(Permission.ALL),
    ): CreateApiTokenOutput {
        requireValidAuth()
        return executeRawQuery(
            Query(
                CreateApiTokenMutation,
                {
                    add("name", name)
                    add("permissions", permissions)
                },
            ) {
                val tokenJson = requireNotNull(it["create_api_token"]) { "No token found in response" }
                serializerFormat.decodeFromJsonElement(tokenJson)
            },
        )!!
    }

    /**
     * Delete an API token for the current account.
     *
     * @param tokenId The ID of the token to delete.
     * @return True if the token was successfully deleted, false otherwise.
     */
    suspend fun deleteApiToken(tokenId: String): Boolean {
        requireValidAuth()
        return executeRawQuery(
            Query(
                DeleteApiTokenMutation,
                { add("api_token_id", tokenId) },
            ) {
                return@Query true
            },
        ) ?: false
    }

    /**
     * Creates an L1 Bitcoin wallet address for a given node which can be used to deposit or withdraw funds.
     *
     * @param nodeId The ID of the node to create the wallet address for.
     * @return The newly created L1 wallet address.
     */
    suspend fun createNodeWalletAddress(nodeId: String): String {
        requireValidAuth()
        return executeRawQuery(
            Query(
                CreateNodeWalletAddressMutation,
                {
                    add("node_id", nodeId)
                },
                signingNodeId = nodeId,
            ) {
                val addressString =
                    requireNotNull(it["create_node_wallet_address"]?.jsonObject?.get("wallet_address")) {
                        "No address found in response"
                    }
                addressString.jsonPrimitive.content
            },
        )!!
    }

    /**
     * @return The current account if one exists, null otherwise.
     */
    suspend fun getCurrentAccount(): Account? {
        requireValidAuth()
        return executeRawQuery(
            Query(
                CurrentAccountQuery,
                {},
            ) {
                val accountJson = requireNotNull(it["current_account"]) { "No account found in response" }
                serializerFormat.decodeFromJsonElement(accountJson)
            },
        )
    }

    /**
     * Adds funds to a Lightspark node on the REGTEST network. If the amount is not specified, 10,000,000 SATOSHI will be
     * added. This API only functions for nodes created on the REGTEST network and will return an error when called for
     * any non-REGTEST node.
     *
     * @param nodeId The ID of the node to fund. Must be a REGTEST node.
     * @param amountSats The amount of funds to add to the node. Defaults to 10,000,000 SATOSHI.
     * @return The amount of funds added to the node.
     */
    suspend fun fundNode(nodeId: String, amountSats: Long?): CurrencyAmount {
        requireValidAuth()
        return executeRawQuery(
            Query(
                FundNodeMutation,
                {
                    add("node_id", nodeId)
                    add("amount_sats", amountSats)
                },
                signingNodeId = nodeId,
            ) {
                val amountJson =
                    requireNotNull(it["fund_node"]?.jsonObject?.get("amount")) { "No amount found in response" }
                serializerFormat.decodeFromJsonElement(amountJson)
            },
        )!!
    }

    /**
     * Withdraws funds from the account and sends it to the requested bitcoin address.
     *
     * Depending on the chosen mode, it will first take the funds from the wallet, and if applicable, close channels
     * appropriately to recover enough funds and reopen channels with the remaining funds.
     * The process is asynchronous and may take up to a few minutes. You can check the progress by polling the
     * `WithdrawalRequest` that is created, or by subscribing to a webhook.
     *
     * @param amountSats The amount of funds to withdraw in SATOSHI.
     * @param bitcoinAddress The Bitcoin address to withdraw funds to.
     * @param mode The mode to use for the withdrawal. See `WithdrawalMode` for more information.
     */
    suspend fun requestWithdrawal(
        amountSats: Long,
        bitcoinAddress: String,
        mode: WithdrawalMode,
    ): WithdrawalRequest {
        requireValidAuth()
        return executeRawQuery(
            Query(
                RequestWithdrawalMutation,
                {
                    add("amount_sats", amountSats)
                    add("bitcoin_address", bitcoinAddress)
                    add("withdrawal_mode", mode)
                },
            ) {
                val withdrawalJson =
                    requireNotNull(it["request_withdrawal"]?.jsonObject?.get("request")) {
                        "No withdrawal request found in response"
                    }
                serializerFormat.decodeFromJsonElement(withdrawalJson)
            },
        )!!
    }

    /**
     * Sends a payment directly to a node on the Lightning Network through the public key of the node without an invoice.
     *
     * @param payerNodeId The ID of the node that will send the payment.
     * @param destinationPublicKey The public key of the destination node.
     * @param amountMsats The amount to pay in milli-satoshis.
     * @param maxFeesMsats The maximum amount of fees that you want to pay for this payment to be sent.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param timeoutSecs The timeout in seconds that we will try to make the payment.
     * @return An `OutgoingPayment` object if the payment was successful, or throws if the payment failed.
     * @throws LightsparkException if the payment failed.
     */
    suspend fun sendPayment(
        payerNodeId: String,
        destinationPublicKey: String,
        amountMsats: Long,
        maxFeesMsats: Long,
        timeoutSecs: Int? = null,
    ): OutgoingPayment {
        requireValidAuth()
        return executeRawQuery(
            Query(
                SendPaymentMutation,
                {
                    add("node_id", payerNodeId)
                    add("destination_public_key", destinationPublicKey)
                    add("amount_msats", amountMsats)
                    add("timeout_secs", timeoutSecs)
                    add("maximum_fees_msats", maxFeesMsats)
                },
                signingNodeId = payerNodeId,
            ) {

                val paymentJson =
                    requireNotNull(it["send_payment"]?.jsonObject?.get("payment")) {
                        "No payment found in response"
                    }
                val failureMessage = paymentJson.jsonObject["outgoing_payment_failure_message"]
                if (failureMessage != null) {
                    throw LightsparkException(
                        failureMessage.jsonObject["rich_text_text"]?.jsonPrimitive?.content ?: "Unknown error",
                        LightsparkErrorCode.PAYMENT_ERROR,
                    )
                }
                serializerFormat.decodeFromJsonElement(paymentJson)
            },
        )!!
    }

    // TODO: Add support for the transaction subscription query.

    suspend fun <T> executeRawQuery(query: Query<T>): T? {
        return requester.executeQuery(query)
    }

    internal fun requireValidAuth() {
        if (!authProvider.isAccountAuthorized()) {
            throw LightsparkAuthenticationException()
        }
    }

    fun setServerEnvironment(environment: ServerEnvironment, invalidateAuth: Boolean) {
        serverUrl = environment.graphQLUrl
        if (invalidateAuth) {
            authProvider = StubAuthProvider()
        }
        requester = Requester(nodeKeyCache, authProvider, serverUrl)
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
        private var serverUrl: String = "api.lightspark.com"
        private var defaultBitcoinNetwork: BitcoinNetwork = BitcoinNetwork.MAINNET
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
                defaultBitcoinNetwork = defaultBitcoinNetwork,
            )
        }
    }
}
