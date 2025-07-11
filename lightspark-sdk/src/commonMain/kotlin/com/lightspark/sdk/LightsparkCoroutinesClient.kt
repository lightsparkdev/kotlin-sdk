@file:JvmName("-LightsparkTestClient")

package com.lightspark.sdk

import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.core.auth.*
import com.lightspark.sdk.core.crypto.NodeKeyCache
import com.lightspark.sdk.core.crypto.SigningKeyDecryptor
import com.lightspark.sdk.core.crypto.SigningKeyLoader
import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.core.requester.Requester
import com.lightspark.sdk.core.requester.ServerEnvironment
import com.lightspark.sdk.crypto.PasswordRecoverySigningKeyLoader
import com.lightspark.sdk.graphql.*
import com.lightspark.sdk.model.*
import com.lightspark.sdk.util.serializerFormat
import java.security.MessageDigest
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.*

private const val SCHEMA_ENDPOINT = "graphql/server/2023-09-13"

/**
 * Main entry point for the Lightspark SDK.
 *
 * ```kotlin
 * // Initialize the client with account token info:
 * val lightsparkClient = LightsparkCoroutinesClient(ClientConfig(
 *     authProvider = AccountApiTokenAuthProvider(
 *         tokenId = "your-token-id",
 *         token = "your-secret-token"
 *     )
 * ))
 *
 * // An example API call fetching the dashboard info for the active account:
 * val dashboard = lightsparkClient.getFullAccountDashboard()
 * ```
 *
 * Note: This client object keeps a local cache in-memory, so a single instance should be reused
 * throughout the lifetime of your app.
 */
class LightsparkCoroutinesClient private constructor(
    private var authProvider: AuthProvider,
    private var serverUrl: String = ServerEnvironment.PROD.graphQLUrl,
    private var defaultBitcoinNetwork: BitcoinNetwork = BitcoinNetwork.REGTEST,
    private val keyDecryptor: SigningKeyDecryptor = SigningKeyDecryptor(),
) {
    private val nodeKeyCache: NodeKeyCache = NodeKeyCache()
    internal var requester = Requester(nodeKeyCache, authProvider, serializerFormat, SCHEMA_ENDPOINT, serverUrl)

    constructor(config: ClientConfig) : this(
        config.authProvider ?: StubAuthProvider(),
        config.serverUrl,
        config.defaultBitcoinNetwork,
    )

    /**
     * Override the auth token provider for this client to provide custom headers on all API calls.
     */
    fun setAuthProvider(authProvider: AuthProvider) {
        nodeKeyCache.clear()
        this.authProvider = authProvider
        requester = Requester(nodeKeyCache, authProvider, serializerFormat, SCHEMA_ENDPOINT, serverUrl)
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
    ): AccountDashboard {
        requireValidAuth()
        return executeQuery(
            Query(
                AccountDashboardQuery,
                {
                    add("network", serializerFormat.encodeToJsonElement(bitcoinNetwork))
                    nodeIds?.let { add("nodeIds", serializerFormat.encodeToJsonElement(nodeIds)) }
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
        return executeQuery(
            Query(
                SingleNodeDashboardQuery,
                {
                    add("network", serializerFormat.encodeToJsonElement(bitcoinNetwork))
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
     * Marks a payment preimage as released. To be used when the recipient has received the payment.
     *
     * @param invoiceId The invoice the preimage belongs to.
     * @param paymentPreimage The preimage to release.
     */
    suspend fun releasePaymentPreimage(invoiceId: String, paymentPreimage: String): ReleasePaymentPreimageOutput {
        requireValidAuth()

        return executeQuery(
            Query(
                ReleasePaymentPreimageMutation,
                {
                    add("invoice_id", invoiceId)
                    add("payment_preimage", paymentPreimage)
                },
            ) {
                val releasePaymentPreimageJson =
                    requireNotNull(it["release_payment_preimage"]) { "Invalid response for payment preimage release" }
                serializerFormat.decodeFromJsonElement<ReleasePaymentPreimageOutput>(releasePaymentPreimageJson)
            },
        )
    }

    /**
     * Creates a lightning invoice for the given node.
     *
     * Test mode note: You can simulate a payment of this invoice in test move using [createTestModePayment].
     *
     * @param nodeId The ID of the node for which to create the invoice.
     * @param amountMsats The amount of the invoice in milli-satoshis.
     * @param memo Optional memo to include in the invoice.
     * @param type The type of invoice to create. Defaults to [InvoiceType.STANDARD].
     * @param expirySecs The number of seconds until the invoice expires. Defaults to 1 day.
     * @param paymentHash Optional payment hash to include in the invoice.
     * @param preimageNonce Optional preimage nonce to include in the invoice.
     */
    suspend fun createInvoice(
        nodeId: String,
        amountMsats: Long,
        memo: String? = null,
        type: InvoiceType = InvoiceType.STANDARD,
        expirySecs: Int? = null,
        paymentHash: String? = null,
        preimageNonce: String? = null,
    ): Invoice {
        requireValidAuth()
        return executeQuery(
            Query(
                CreateInvoiceMutation,
                {
                    add("nodeId", nodeId)
                    add("amountMsats", amountMsats)
                    memo?.let { add("memo", memo) }
                    add("type", serializerFormat.encodeToJsonElement(type))
                    expirySecs?.let { add("expirySecs", expirySecs) }
                    paymentHash?.let { add("paymentHash", paymentHash) }
                    preimageNonce?.let { add("preimageNonce", preimageNonce) }
                },
            ) {
                val invoiceJson =
                    requireNotNull(
                        it["create_invoice"]?.jsonObject?.get("invoice"),
                    ) { "No invoice found in response" }
                serializerFormat.decodeFromJsonElement(invoiceJson)
            },
        )
    }

    /**
     * Creates a Lightning invoice for the given node. This should only be used for generating invoices for LNURLs, with
     * [LightsparkCoroutinesClient.createInvoice] preferred in the general case.
     *
     * @param nodeId The ID of the node for which to create the invoice.
     * @param amountMsats The amount of the invoice in milli-satoshis.
     * @param metadata The LNURL metadata payload field from the initial payreq response. This will be hashed and
     *      present in the h-tag (SHA256 purpose of payment) of the resulting Bolt 11 invoice.
     * @param expirySecs The number of seconds until the invoice expires. Defaults to 1 day.
     * @param paymentHash Optional payment hash to include in the invoice.
     * @param preimageNonce Optional preimage nonce to include in the invoice.
     */
    suspend fun createLnurlInvoice(
        nodeId: String,
        amountMsats: Long,
        metadata: String,
        expirySecs: Int? = null,
        paymentHash: String? = null,
        preimageNonce: String? = null,
    ): Invoice {
        requireValidAuth()

        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(metadata.toByteArray())
        val metadataHash = digest.fold(StringBuilder()) { sb, it -> sb.append("%02x".format(it)) }.toString()

        return executeQuery(
            Query(
                CreateLnurlInvoiceMutation,
                {
                    add("nodeId", nodeId)
                    add("amountMsats", amountMsats)
                    add("metadataHash", metadataHash)
                    expirySecs?.let { add("expirySecs", expirySecs) }
                    paymentHash?.let { add("paymentHash", paymentHash) }
                    preimageNonce?.let { add("preimageNonce", preimageNonce) }
                },
            ) {
                val invoiceJson =
                    requireNotNull(
                        it["create_lnurl_invoice"]?.jsonObject?.get("invoice"),
                    ) { "No invoice found in response" }
                serializerFormat.decodeFromJsonElement(invoiceJson)
            },
        )
    }

    /**
     * Creates a Lightning invoice for the given node. This should only be used for generating invoices for UMA, with
     * [LightsparkCoroutinesClient.createInvoice] preferred in the general case.
     *
     * @param nodeId The ID of the node for which to create the invoice.
     * @param amountMsats The amount of the invoice in milli-satoshis.
     * @param metadata The LNURL metadata payload field from the initial payreq response. This will be hashed and
     *      present in the h-tag (SHA256 purpose of payment) of the resulting Bolt 11 invoice.
     * @param expirySecs The number of seconds until the invoice expires. Defaults to 1 day.
     * @param signingPrivateKey The receiver's signing private key. Used to hash the receiver identifier.
     * @param receiverIdentifier Optional identifier of the receiver. If provided, this will be hashed using a
     *      monthly-rotated seed and used for anonymized analysis.
     * @param paymentHash Optional payment hash to include in the invoice.
     * @param preimageNonce Optional preimage nonce to include in the invoice.
     */
    @Throws(IllegalArgumentException::class)
    suspend fun createUmaInvoice(
        nodeId: String,
        amountMsats: Long,
        metadata: String,
        expirySecs: Int? = null,
        signingPrivateKey: ByteArray? = null,
        receiverIdentifier: String? = null,
        paymentHash: String? = null,
        preimageNonce: String? = null,
    ): Invoice {
        requireValidAuth()

        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(metadata.toByteArray())
        val metadataHash = digest.fold(StringBuilder()) { sb, it -> sb.append("%02x".format(it)) }.toString()
        val receiverHash = receiverIdentifier?.let {
            if (signingPrivateKey == null) {
                throw IllegalArgumentException("Receiver identifier provided without signing private key")
            } else {
                hashUmaIdentifier(receiverIdentifier, signingPrivateKey)
            }
        }

        return executeQuery(
            Query(
                CreateUmaInvoiceMutation,
                {
                    add("nodeId", nodeId)
                    add("amountMsats", amountMsats)
                    add("metadataHash", metadataHash)
                    expirySecs?.let { add("expirySecs", expirySecs) }
                    receiverHash?.let { add("receiverHash", receiverHash) }
                    paymentHash?.let { add("paymentHash", paymentHash) }
                    preimageNonce?.let { add("preimageNonce", preimageNonce) }
                },
            ) {
                val invoiceJson =
                    requireNotNull(
                        it["create_uma_invoice"]?.jsonObject?.get("invoice"),
                    ) { "No invoice found in response" }
                serializerFormat.decodeFromJsonElement(invoiceJson)
            },
        )
    }

    /**
     * Cancels an existing unpaid invoice and returns that invoice. Cancelled invoices cannot be paid.
     *
     * @param invoiceId The ID of the invoice to cancel.
     * @return The cancelled invoice.
     */
    suspend fun cancelInvoice(invoiceId: String): Invoice {
        requireValidAuth()
        return executeQuery(
            Query(
                CancelInvoiceMutation,
                {
                    add("invoiceId", invoiceId)
                },
            ) {
                val invoiceJson =
                    requireNotNull(
                        it["cancel_invoice"]?.jsonObject?.get("invoice"),
                    ) { "No invoice found in response" }
                serializerFormat.decodeFromJsonElement(invoiceJson)
            },
        )
    }

    /**
     * Pay a lightning invoice for the given node.
     *
     * Note: This call will fail if the node sending the payment is not unlocked yet via the [loadNodeSigningKey]
     * function. You must successfully unlock the node with its password before calling this function.
     *
     * Test mode note: For test mode, you can use the [createTestModeInvoice] function to create an invoice you can
     * pay in test mode.
     *
     * @param nodeId The ID of the node which will pay the invoice.
     * @param encodedInvoice An encoded string representation of the invoice to pay.
     * @param maxFeesMsats The maximum fees to pay in milli-satoshis. You must pass a value.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param amountMsats The amount to pay in milli-satoshis. Defaults to the full amount of the invoice.
     * @param timeoutSecs The number of seconds to wait for the payment to complete. Defaults to 60.
     * @param idempotencyKey An optional key to ensure idempotency of the payment. If provided, the same result will be
     *     returned for the same idempotency key without triggering a new payment.
     * @return The payment details.
     */
    @JvmOverloads
    suspend fun payInvoice(
        nodeId: String,
        encodedInvoice: String,
        maxFeesMsats: Long,
        amountMsats: Long? = null,
        timeoutSecs: Int = 60,
        idempotencyKey: String? = null,
    ): OutgoingPayment {
        requireValidAuth()
        return executeQuery(
            Query(
                PayInvoiceMutation,
                {
                    add("node_id", nodeId)
                    add("encoded_invoice", encodedInvoice)
                    add("timeout_secs", timeoutSecs)
                    add("maximum_fees_msats", maxFeesMsats)
                    amountMsats?.let { add("amount_msats", amountMsats) }
                    idempotencyKey?.let { add("idempotency_key", idempotencyKey) }
                },
                signingNodeId = nodeId,
            ) {
                val paymentJson =
                    requireNotNull(it["pay_invoice"]?.jsonObject?.get("payment")) { "No payment found in response" }
                serializerFormat.decodeFromJsonElement(paymentJson)
            },
        )
    }

    /**
     * [payUmaInvoice] sends an UMA payment to a node on the Lightning Network, based on the invoice (as defined by the
     * BOLT11 specification) that you provide. This should only be used for paying UMA invoices, with [payInvoice]
     * preferred in the general case.
     *
     * @param nodeId The ID of the node which will pay the invoice.
     * @param encodedInvoice An encoded string representation of the invoice to pay.
     * @param maxFeesMsats The maximum fees to pay in milli-satoshis. You must pass a value.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param amountMsats The amount to pay in milli-satoshis. Defaults to the full amount of the invoice.
     * @param timeoutSecs The number of seconds to wait for the payment to complete. Defaults to 60.
     * @param signingPrivateKey The sender's signing private key. Used to hash the sender identifier.
     * @param senderIdentifier Optional identifier of the sender. If provided, this will be hashed using a
     *      monthly-rotated seed and used for anonymized analysis.
     * @param idempotencyKey An optional key to ensure idempotency of the payment. If provided, the same result will be
     *     returned for the same idempotency key without triggering a new payment.
     * @return The payment details.
     */
    @JvmOverloads
    @Throws(IllegalArgumentException::class)
    suspend fun payUmaInvoice(
        nodeId: String,
        encodedInvoice: String,
        maxFeesMsats: Long,
        amountMsats: Long? = null,
        timeoutSecs: Int = 60,
        signingPrivateKey: ByteArray? = null,
        senderIdentifier: String? = null,
        idempotencyKey: String? = null,
    ): OutgoingPayment {
        requireValidAuth()

        val senderHash = senderIdentifier?.let {
            if (signingPrivateKey == null) {
                throw IllegalArgumentException("Sender identifier provided without signing private key")
            } else {
                hashUmaIdentifier(senderIdentifier, signingPrivateKey)
            }
        }

        return executeQuery(
            Query(
                PayUmaInvoiceMutation,
                {
                    add("node_id", nodeId)
                    add("encoded_invoice", encodedInvoice)
                    add("timeout_secs", timeoutSecs)
                    add("maximum_fees_msats", maxFeesMsats)
                    amountMsats?.let { add("amount_msats", amountMsats) }
                    senderHash?.let { add("sender_hash", senderHash) }
                    idempotencyKey?.let { add("idempotency_key", idempotencyKey) }
                },
                signingNodeId = nodeId,
            ) {
                val paymentJson =
                    requireNotNull(it["pay_uma_invoice"]?.jsonObject?.get("payment")) { "No payment found in response" }
                serializerFormat.decodeFromJsonElement(paymentJson)
            },
        )
    }

    /**
     * Decode a lightning invoice to get its details included payment amount, destination, etc.
     *
     * @param encodedInvoice An encoded string representation of the invoice to decode.
     * @return The decoded invoice details.
     */
    suspend fun decodeInvoice(
        encodedInvoice: String,
    ): InvoiceData {
        return executeQuery(
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
     * Get the L1 fee estimate for a deposit or withdrawal.
     *
     * @param bitcoinNetwork The bitcoin network to use for the fee estimate. Defaults to the network set in the gradle
     *      project properties.
     * @return The fee estimate including a fast and minimum fee as [CurrencyAmount]s
     */
    suspend fun getBitcoinFeeEstimate(bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork): FeeEstimate {
        requireValidAuth()
        return executeQuery(
            Query(
                BitcoinFeeEstimateQuery,
                { add("bitcoin_network", serializerFormat.encodeToJsonElement(bitcoinNetwork)) },
            ) {
                val feeEstimateJson =
                    requireNotNull(it["bitcoin_fee_estimate"]) { "No fee estimate found in response" }
                serializerFormat.decodeFromJsonElement(feeEstimateJson)
            },
        )
    }

    /**
     * Gets an estimate of the fees that will be paid for a Lightning invoice.
     *
     * @param nodeId The node from where you want to send the payment.
     * @param encodedPaymentRequest The invoice you want to pay (as defined by the BOLT11 standard).
     * @param amountMsats If the invoice does not specify a payment amount, then the amount that you wish to pay,
     *     expressed in msats.
     * @returns An estimate of the fees that will be paid for a Lightning invoice.
     */
    @JvmOverloads
    suspend fun getLightningFeeEstimateForInvoice(
        nodeId: String,
        encodedPaymentRequest: String,
        amountMsats: Long? = null,
    ): CurrencyAmount {
        requireValidAuth()
        return executeQuery(
            Query(
                LightningFeeEstimateForInvoiceQuery,
                {
                    add("node_id", nodeId)
                    add("encoded_payment_request", encodedPaymentRequest)
                    amountMsats?.let { add("amount_msats", amountMsats) }
                },
            ) {
                val feeEstimateJson =
                    requireNotNull(it["lightning_fee_estimate_for_invoice"]) { "No fee estimate found in response" }
                val lightningFeeEstimateOutput =
                    serializerFormat.decodeFromJsonElement<LightningFeeEstimateOutput>(feeEstimateJson)
                lightningFeeEstimateOutput.feeEstimate
            },
        )
    }

    /**
     * Returns an estimate of the fees that will be paid to send a payment to another Lightning node.
     *
     * @param nodeId The node from where you want to send the payment.
     * @param destinationNodePublicKey The public key of the node that you want to pay.
     * @param amountMsats The payment amount expressed in msats.
     * @returns An estimate of the fees that will be paid to send a payment to another Lightning node.
     */
    suspend fun getLightningFeeEstimateForNode(
        nodeId: String,
        destinationNodePublicKey: String,
        amountMsats: Long,
    ): CurrencyAmount {
        requireValidAuth()
        return executeQuery(
            Query(
                LightningFeeEstimateForNodeQuery,
                {
                    add("node_id", nodeId)
                    add("destination_node_public_key", destinationNodePublicKey)
                    add("amount_msats", amountMsats)
                },
            ) {
                val feeEstimateJson =
                    requireNotNull(it["lightning_fee_estimate_for_node"]) { "No fee estimate found in response" }
                val lightningFeeEstimateOutput =
                    serializerFormat.decodeFromJsonElement<LightningFeeEstimateOutput>(feeEstimateJson)
                lightningFeeEstimateOutput.feeEstimate
            },
        )
    }

    /**
     * Returns an estimated amount for the L1 withdrawal fees for the specified node, amount, and strategy.
     *
     * @param nodeId The node from where you want to send the payment.
     * @param amountSats The amount of funds to withdraw in SATOSHI. Use -1 to withdrawal all funds from this node.
     * @param mode The mode to use for the withdrawal. See `WithdrawalMode` for more information.
     * @returns An estimate of the fees that will be paid to withdraw funds for the node, amount, and strategy.
     */
    suspend fun getWithdrawalFeeEstimate(
        nodeId: String,
        amountSats: Long,
        mode: WithdrawalMode,
    ): CurrencyAmount {
        requireValidAuth()
        return executeQuery(
            Query(
                WithdrawalFeeEstimateQuery,
                {
                    add("node_id", nodeId)
                    add("amount_sats", amountSats)
                    add("withdrawal_mode", serializerFormat.encodeToJsonElement(mode))
                },
            ) {
                val feeEstimateJson =
                    requireNotNull(it["withdrawal_fee_estimate"]) { "No fee estimate found in response" }
                val withdrawalFeeEstimateOutput =
                    serializerFormat.decodeFromJsonElement<WithdrawalFeeEstimateOutput>(feeEstimateJson)
                withdrawalFeeEstimateOutput.feeEstimate
            },
        )
    }

    /**
     * @return A [Flow] that emits the set of node IDs that have been unlocked via the [recoverNodeSigningKey] function.
     */
    fun getUnlockedNodeIds(): Flow<Set<String>> = nodeKeyCache.observeCachedNodeIds()

    /**
     * Unlocks a node for use with the SDK for the current application session. This function or [loadNodeSigningKey]
     * must be called before any other functions that require node signing keys, including [payInvoice].
     *
     * @param nodeId The ID of the node to unlock.
     * @param nodePassword The password for the node.
     * @return True if the node was successfully unlocked, false otherwise.
     * @deprecated Use [loadNodeSigningKey] instead.
     */
    @Deprecated("Use loadNodeSigningKey instead")
    suspend fun recoverNodeSigningKey(
        nodeId: String,
        nodePassword: String,
    ): Boolean {
        requireValidAuth()
        return try {
            val signingKeyLoader = PasswordRecoverySigningKeyLoader(nodeId, nodePassword, keyDecryptor)
            loadNodeSigningKey(nodeId, signingKeyLoader)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Unlocks a node for use with the SDK for the current application session. This function must be called before any
     * other functions that require node signing keys, including [payInvoice].
     *
     * It is the responsibility of the application to ensure that the key is valid and that it is the correct key for
     * the node. Otherwise signed requests will fail.
     *
     * @param nodeId The ID of the node to unlock.
     * @param signingKeyLoader An implementation of [SigningKeyLoader] which will be used to load the signing key for
     *     the node. For example, [PasswordRecoverySigningKeyLoader] can be used to load RSA signing key for an OSK node
     *     using the node's password. If the node is using remote signing, you can use [Secp256k1SigningKeyLoader] to
     *     generate the correct signing key using your node's master seed.
     */
    suspend fun loadNodeSigningKey(nodeId: String, signingKeyLoader: SigningKeyLoader) {
        requireValidAuth()
        nodeKeyCache[nodeId] = signingKeyLoader.loadSigningKey(requester)
    }

    /**
     * Create a new API token for the current account.
     *
     * @param name Creates a new API token that can be used to authenticate requests for this account when using the
     *     Lightspark APIs and SDKs.
     * @param transact Whether the token should be able to transact or only view data.
     * @param testMode True if the token should be able to access only testnet false to access only mainnet.
     * @return The newly created token.
     */
    suspend fun createApiToken(
        name: String,
        transact: Boolean,
        testMode: Boolean,
    ): CreateApiTokenOutput {
        requireValidAuth()
        val permissions = if (transact && testMode) {
            listOf(Permission.REGTEST_VIEW, Permission.REGTEST_TRANSACT)
        } else if (transact) {
            listOf(Permission.MAINNET_VIEW, Permission.MAINNET_TRANSACT)
        } else if (testMode) {
            listOf(Permission.REGTEST_VIEW)
        } else {
            listOf(Permission.MAINNET_VIEW)
        }

        return checkNotNull(
            executeQuery(
                Query(
                    CreateApiTokenMutation,
                    {
                        add("name", name)
                        add("permissions", serializerFormat.encodeToJsonElement(permissions.map { it }))
                    },
                ) {
                    val tokenJson = requireNotNull(it["create_api_token"]) { "No token found in response" }
                    serializerFormat.decodeFromJsonElement(tokenJson)
                },
            ),
        ) { "No token found in createApiToken response" }
    }

    /**
     * Delete an API token for the current account.
     *
     * @param tokenId The ID of the token to delete.
     * @return True if the token was successfully deleted, false otherwise.
     */
    suspend fun deleteApiToken(tokenId: String): Boolean {
        requireValidAuth()
        return executeQuery(
            Query(
                DeleteApiTokenMutation,
                { add("api_token_id", tokenId) },
            ) {
                return@Query true
            },
        )
    }

    /**
     * Creates an L1 Bitcoin wallet address for a given node which can be used to deposit or withdraw funds.
     *
     * @param nodeId The ID of the node to create the wallet address for.
     * @return The newly created L1 wallet address.
     */
    suspend fun createNodeWalletAddress(nodeId: String): String {
        requireValidAuth()
        return executeQuery(
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
        )
    }

    /**
     * @return The current account if one exists, throws an exception if not logged in with a valid account.
     */
    suspend fun getCurrentAccount(): Account {
        requireValidAuth()
        return executeQuery(
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
     * @param fundingAddress: L1 address owned by funded node. If null, automatically create new funding address
     * @return The amount of funds added to the node.
     */
    suspend fun fundNode(
        nodeId: String,
        amountSats: Long?,
        fundingAddress: String? = null,
    ): CurrencyAmount {
        requireValidAuth()
        return executeQuery(
            Query(
                FundNodeMutation,
                {
                    add("node_id", nodeId)
                    amountSats?.let { add("amount_sats", it) }
                    fundingAddress?.let { add("funding_address", it) }
                },
                signingNodeId = nodeId,
            ) {
                val amountJson =
                    requireNotNull(it["fund_node"]?.jsonObject?.get("amount")) { "No amount found in response" }
                serializerFormat.decodeFromJsonElement(amountJson)
            },
        )
    }

    /**
     * Withdraws funds from the account and sends it to the requested bitcoin address.
     *
     * Depending on the chosen mode, it will first take the funds from the wallet, and if applicable, close channels
     * appropriately to recover enough funds and reopen channels with the remaining funds.
     * The process is asynchronous and may take up to a few minutes. You can check the progress by polling the
     * `WithdrawalRequest` that is created, or by subscribing to a webhook.
     *
     * @param nodeId The ID of the node to withdraw funds from.
     * @param amountSats The amount of funds to withdraw in SATOSHI.
     * @param bitcoinAddress The Bitcoin address to withdraw funds to.
     * @param mode The mode to use for the withdrawal. See `WithdrawalMode` for more information.
     * @param idempotencyKey An optional key to ensure idempotency of the withdrawal. If provided, the same result will
     *     be returned for the same idempotency key without triggering a new withdrawal.
     */
    @JvmOverloads
    suspend fun requestWithdrawal(
        nodeId: String,
        amountSats: Long,
        bitcoinAddress: String,
        mode: WithdrawalMode,
        idempotencyKey: String? = null,
    ): WithdrawalRequest {
        requireValidAuth()
        return executeQuery(
            Query(
                RequestWithdrawalMutation,
                {
                    add("node_id", nodeId)
                    add("amount_sats", amountSats)
                    add("bitcoin_address", bitcoinAddress)
                    add("withdrawal_mode", serializerFormat.encodeToJsonElement(mode))
                    idempotencyKey?.let { add("idempotency_key", idempotencyKey) }
                },
                signingNodeId = nodeId,
            ) {
                val withdrawalJson =
                    requireNotNull(it["request_withdrawal"]?.jsonObject?.get("request")) {
                        "No withdrawal request found in response"
                    }
                serializerFormat.decodeFromJsonElement(withdrawalJson)
            },
        )
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
     * @param idempotencyKey An optional key to ensure idempotency of the payment. If provided, the same result will be
     *     returned for the same idempotency key without triggering a new payment.
     * @return An `OutgoingPayment` object if the payment was successful, or throws if the payment failed.
     * @throws LightsparkException if the payment failed.
     */
    @JvmOverloads
    suspend fun sendPayment(
        payerNodeId: String,
        destinationPublicKey: String,
        amountMsats: Long,
        maxFeesMsats: Long,
        timeoutSecs: Int = 60,
        idempotencyKey: String? = null,
    ): OutgoingPayment {
        requireValidAuth()
        return executeQuery(
            Query(
                SendPaymentMutation,
                {
                    add("node_id", payerNodeId)
                    add("destination_public_key", destinationPublicKey)
                    add("amount_msats", amountMsats)
                    add("timeout_secs", timeoutSecs)
                    add("maximum_fees_msats", maxFeesMsats)
                    idempotencyKey?.let { add("idempotency_key", idempotencyKey) }
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
        )
    }

    /**
     * In test mode, generates a Lightning Invoice which can be paid by a local node.
     * This call is only valid in test mode. You can then pay the invoice using [payInvoice].
     *
     * @param localNodeId The ID of the node that will pay the invoice.
     * @param amountMsats The amount to pay in milli-satoshis.
     * @param memo An optional memo to attach to the invoice.
     * @param invoiceType The type of invoice to create.
     */
    suspend fun createTestModeInvoice(
        localNodeId: String,
        amountMsats: Long,
        memo: String? = null,
        invoiceType: InvoiceType? = null,
    ): String {
        requireValidAuth()
        return executeQuery(
            Query(
                CreateTestModeInvoice,
                {
                    add("local_node_id", localNodeId)
                    add("amount_msats", amountMsats)
                    memo?.let { add("memo", memo) }
                    invoiceType?.let { add("invoice_type", serializerFormat.encodeToJsonElement(it)) }
                },
            ) {
                val outputJson =
                    requireNotNull(it["create_test_mode_invoice"]) { "No invoice found in response" }
                val output = serializerFormat.decodeFromJsonElement<CreateTestModeInvoiceOutput>(outputJson)
                output.encodedPaymentRequest
            },
        )
    }

    /**
     * In test mode, simulates a payment of a Lightning Invoice from another node.
     * This can only be used in test mode and should be used with invoices generated by [createTestModeInvoice].
     *
     * @param localNodeId The ID of the node that will receive the payment.
     * @param encodedInvoice The encoded invoice to pay.
     * @param amountMsats The amount to pay in milli-satoshis for 0-amount invoices. This should be null for non-zero
     *     amount invoices.
     */
    suspend fun createTestModePayment(
        localNodeId: String,
        encodedInvoice: String,
        amountMsats: Long? = null,
    ): IncomingPayment {
        requireValidAuth()
        return executeQuery(
            Query(
                CreateTestModePayment,
                {
                    add("local_node_id", localNodeId)
                    add("encoded_invoice", encodedInvoice)
                    amountMsats?.let { add("amount_msats", amountMsats) }
                },
                signingNodeId = localNodeId,
            ) {
                val outputJson =
                    requireNotNull(it["create_test_mode_payment"]) { "No payment output found in response" }
                val paymentJson =
                    requireNotNull(outputJson.jsonObject["incoming_payment"]) { "No payment found in response" }
                serializerFormat.decodeFromJsonElement(paymentJson)
            },
        )
    }

    /**
     * Registers a payment with a compliance provider.
     * This should only be called if you have a Compliance Provider's API Key in settings (like Chainalysis).
     *
     * @param complianceProvider The compliance provider to register the payment with.
     * @param paymentId The ID of the payment to register.
     * @param nodePubKey The public key of the counterparty node which is the recipient node if the payment is an
     *     outgoing payment and the sender node if the payment is an incoming payment.
     * @param direction The direction of the payment.
     * @return The ID of the registered payment.
     */
    suspend fun registerPayment(
        complianceProvider: ComplianceProvider,
        paymentId: String,
        nodePubKey: String,
        direction: PaymentDirection,
    ): String {
        requireValidAuth()
        return executeQuery(
            Query(
                RegisterPaymentMutation,
                {
                    add("provider", serializerFormat.encodeToJsonElement(complianceProvider))
                    add("payment_id", paymentId)
                    add("node_pubkey", nodePubKey)
                    add("direction", serializerFormat.encodeToJsonElement(direction))
                },
            ) {
                val outputJson =
                    requireNotNull(it["register_payment"]) { "No payment output found in response" }
                val paymentIdJson =
                    requireNotNull(outputJson.jsonObject["payment"]?.jsonObject?.get("id")) { "No payment found in response" }
                paymentIdJson.jsonPrimitive.content
            },
        )
    }

    /**
     * Performs sanction screening on a lightning node against a given provider.
     * This should only be called if you have a Compliance Provider's API Key in settings (like Chainalysis).
     *
     * @param complianceProvider The provider that you want to use to perform the screening.
     * @param nodePubKey TThe public key of the node that needs to be screened.
     * @return The risk rating of the node.
     */
    suspend fun screenNode(
        complianceProvider: ComplianceProvider,
        nodePubKey: String,
    ): RiskRating {
        requireValidAuth()
        return executeQuery(
            Query(
                ScreenNodeMutation,
                {
                    add("provider", serializerFormat.encodeToJsonElement(complianceProvider))
                    add("node_pubkey", nodePubKey)
                },
            ) {
                val outputJson =
                    requireNotNull(it["screen_node"]) { "No payment output found in response" }
                val riskRatingJson =
                    requireNotNull(outputJson.jsonObject["rating"]) { "No risk rating found in response" }
                serializerFormat.decodeFromJsonElement(riskRatingJson)
            },
        )
    }

    /**
     * Fetches the outgoing payments (if any) which have been made for a given invoice.
     *
     * @param encodedInvoice The encoded invoice to fetch the payments for.
     * @param transactionStatuses The transaction statuses to filter the payments by. If null, all payments will be
     *    returned.
     * @return The list of outgoing payments for the invoice.
     */
    suspend fun getOutgoingPaymentsForInvoice(
        encodedInvoice: String,
        transactionStatuses: List<TransactionStatus>? = null,
    ): List<OutgoingPayment> {
        requireValidAuth()
        return executeQuery(
            Query(
                OutgoingPaymentsForInvoiceQuery,
                {
                    add("encodedInvoice", encodedInvoice)
                    transactionStatuses?.let {
                        add("transactionStatuses", serializerFormat.encodeToJsonElement(it))
                    }
                },
            ) {
                val outputJson =
                    requireNotNull(it["outgoing_payments_for_invoice"]) { "No payment output found in response" }
                val paymentsJson =
                    requireNotNull(outputJson.jsonObject["payments"]) { "No payments found in response" }
                serializerFormat.decodeFromJsonElement(paymentsJson)
            },
        )
    }

    /**
     * Fetch incoming payments for a given payment hash.
     *
     * @param paymentHash The payment hash of the invoice for which to fetch the incoming payments.
     * @param transactionStatuses The transaction statuses to filter the payments by. If null, all payments will be
     *   returned.
     */
    suspend fun getIncomingPaymentsForPaymentHash(
        paymentHash: String,
        transactionStatuses: List<TransactionStatus>? = null,
    ): List<IncomingPayment> {
        requireValidAuth()
        return executeQuery(
            Query(
                IncomingPaymentsForPaymentHashQuery,
                {
                    add("paymentHash", paymentHash)
                    transactionStatuses?.let {
                        add("transactionStatuses", serializerFormat.encodeToJsonElement(it))
                    }
                },
            ) {
                val outputJson =
                    requireNotNull(it["incoming_payments_for_payment_hash"]) { "No payment output found in response" }
                val paymentsJson =
                    requireNotNull(outputJson.jsonObject["payments"]) { "No payments found in response" }
                serializerFormat.decodeFromJsonElement(paymentsJson)
            },
        )
    }

    /**
     * fetch outgoing payments for a given payment hash
     *
     * @param paymentHash the payment hash of the invoice for which to fetch the outgoing payments
     * @param transactionStatuses the transaction statuses to filter the payments by.  If null, all payments will be returned.
     */
    suspend fun getOutgoingPaymentForPaymentHash(
        paymentHash: String,
        transactionStatuses: List<TransactionStatus>? = null,
    ): List<OutgoingPayment> {
        requireValidAuth()
        return executeQuery(
            Query(
                OutgoingPaymentsForPaymentHashQuery,
                {
                    add("paymentHash", paymentHash)
                    transactionStatuses?.let {
                        add("transactionStatuses", serializerFormat.encodeToJsonElement(it))
                    }
                },
            ) {
                val outputJson =
                    requireNotNull(it["outgoing_payments_for_payment_hash"]) { "No payment output found in response" }
                val paymentsJson =
                    requireNotNull(outputJson.jsonObject["payments"]) { "No payments found in response" }
                serializerFormat.decodeFromJsonElement(paymentsJson)
            },
        )
    }

    /**
     * Fetch outgoing payment for a given idempotency key
     *
     * @param idempotencyKey The idempotency key used when creating the payment.
     */
    suspend fun getOutgoingPaymentForIdempotencyKey(
        idempotencyKey: String,
    ): OutgoingPayment? {
        requireValidAuth()
        return executeQuery(
            Query(
                OutgoingPaymentForIdempotencyKeyQuery,
                {
                    add("idempotency_key", idempotencyKey)
                },
            ) {
                val outputJson =
                    requireNotNull(it["outgoing_payment_for_idempotency_key"]) { "No payment output found in response" }
                val paymentJson = outputJson.jsonObject["payment"]
                if (paymentJson == null) {
                    return@Query null
                }
                serializerFormat.decodeFromJsonElement(paymentJson)
            },
        )
    }

    /**
     * fetch invoice for a given payment hash
     *
     * @param paymentHash the payment hash of the invoice for which to fetch the outgoing payments
     */
    suspend fun getInvoiceForPaymentHash(
        paymentHash: String,
    ): Invoice {
        requireValidAuth()
        return executeQuery(
            Query(
                InvoiceForPaymentHashQuery,
                {
                    add("paymentHash", paymentHash)
                },
            ) {
                val outputJson =
                    requireNotNull(it["invoice_for_payment_hash"]) { "No invoice found in response" }
                val invoiceJson =
                    requireNotNull(outputJson.jsonObject["invoice"]) { "No invoice found in response" }
                serializerFormat.decodeFromJsonElement(invoiceJson)
            },
        )
    }

    /**
     * fetch invoice for a given invoice id
     *
     * @param invoiceId the id of the invoice for which to fetch the outgoing payments
     * @param transactionStatuses the transaction statuses to filter the payments by.  If null, all payments will be returned.
     */
    suspend fun getIncomingPaymentsForInvoice(
        invoiceId: String,
        transactionStatuses: List<TransactionStatus>? = null,
    ): List<IncomingPayment> {
        return executeQuery(
            Query(
                IncomingPaymentsForInvoiceQuery,
                {
                    add("invoiceId", invoiceId)
                    transactionStatuses?.let {
                        add("transactionStatuses", serializerFormat.encodeToJsonElement(it))
                    }
                },
            ) {
                val outputJson =
                    requireNotNull(it["incoming_payments_for_invoice"]) { "No payment output found in response" }
                val paymentsJson =
                    requireNotNull(outputJson.jsonObject["payments"]) { "No payments found in response" }
                serializerFormat.decodeFromJsonElement(paymentsJson)
            },
        )
    }

    /**
     * Creates an UMA invitation. If you are part of the incentive program you should use
     * [createUmaInvitationWithIncentives].
     *
     * @param inviterUma The UMA of the inviter.
     * @return The invitation that was created.
     */
    suspend fun createUmaInvitation(inviterUma: String): UmaInvitation {
        requireValidAuth()
        return executeQuery(
            Query(
                CreateUmaInvitation,
                {
                    add("inviter_uma", inviterUma)
                },
            ) {
                val outputJson =
                    requireNotNull(it["create_uma_invitation"]) { "No invitation output found in response" }
                val invitationJson =
                    requireNotNull(outputJson.jsonObject["invitation"]) { "No invitation found in response" }
                serializerFormat.decodeFromJsonElement(invitationJson)
            },
        )
    }

    /**
     * Creates an UMA invitation as part of the incentive program. If you are not part of the incentive program you
     * should use [createUmaInvitation].
     *
     * @param inviterUma The UMA of the inviter.
     * @param inviterPhoneNumberE164 The phone number of the inviter in E164 format.
     * @param inviterRegionCode The region of the inviter.
     * @return The invitation that was created.
     */
    @Throws(IllegalArgumentException::class)
    suspend fun createUmaInvitationWithIncentives(
        inviterUma: String,
        inviterPhoneNumberE164: String,
        inviterRegionCode: RegionCode,
    ): UmaInvitation {
        requireValidAuth()
        return executeQuery(
            Query(
                CreateUmaInvitationWithIncentives,
                {
                    add("inviter_uma", inviterUma)
                    add("inviter_phone_hash", hashE164PhoneNumber(inviterPhoneNumberE164))
                    add("inviter_region", inviterRegionCode.rawValue)
                },
            ) {
                val outputJson =
                    requireNotNull(it["create_uma_invitation"]) { "No invitation output found in response" }
                val invitationJson =
                    requireNotNull(outputJson.jsonObject["invitation"]) { "No invitation found in response" }
                serializerFormat.decodeFromJsonElement(invitationJson)
            },
        )
    }

    /**
     * Claims an UMA invitation. If you are part of the incentive program, you should use
     * [claimUmaInvitationWithIncentives].
     *
     * @param invitationCode The invitation code to claim.
     * @param inviteeUma The UMA of the invitee.
     * @returns The invitation that was claimed.
     */
    suspend fun claimUmaInvitation(invitationCode: String, inviteeUma: String): UmaInvitation {
        requireValidAuth()
        return executeQuery(
            Query(
                ClaimUmaInvitation,
                {
                    add("invitation_code", invitationCode)
                    add("invitee_uma", inviteeUma)
                },
            ) {
                val outputJson =
                    requireNotNull(it["claim_uma_invitation"]) { "No invitation output found in response" }
                val invitationJson =
                    requireNotNull(outputJson.jsonObject["invitation"]) { "No invitation found in response" }
                serializerFormat.decodeFromJsonElement(invitationJson)
            },
        )
    }

    /**
     * Claims an UMA invitation as part of the incentive program. If you are not part of the incentive program, you
     * should use [claimUmaInvitation].
     *
     * @param invitationCode The invitation code to claim.
     * @param inviteeUma The UMA of the invitee.
     * @param inviteePhoneNumberE164 The phone number of the invitee in E164 format.
     * @param inviteeRegionCode The region of the invitee.
     * @returns The invitation that was claimed.
     */
    @Throws(IllegalArgumentException::class)
    suspend fun claimUmaInvitationWithIncentives(
        invitationCode: String,
        inviteeUma: String,
        inviteePhoneNumberE164: String,
        inviteeRegionCode: RegionCode,
    ): UmaInvitation {
        requireValidAuth()
        return executeQuery(
            Query(
                ClaimUmaInvitationWithIncentives,
                {
                    add("invitation_code", invitationCode)
                    add("invitee_uma", inviteeUma)
                    add("invitee_phone_hash", hashE164PhoneNumber(inviteePhoneNumberE164))
                    add("invitee_region", inviteeRegionCode.rawValue)
                },
            ) {
                val outputJson =
                    requireNotNull(it["claim_uma_invitation"]) { "No invitation output found in response" }
                val invitationJson =
                    requireNotNull(outputJson.jsonObject["invitation"]) { "No invitation found in response" }
                serializerFormat.decodeFromJsonElement(invitationJson)
            },
        )
    }

    /**
     * Fetches an UMA invitation by its invitation code.
     *
     * @param invitationCode The code of the invitation to fetch.
     * @returns The invitation with the given code, or null if no invitation exists with that code.
     */
    suspend fun fetchUmaInvitation(invitationCode: String): UmaInvitation {
        requireValidAuth()
        return executeQuery(
            Query(
                FetchUmaInvitation,
                {
                    add("invitation_code", invitationCode)
                },
            ) {
                val outputJson =
                    requireNotNull(it["uma_invitation_by_code"]) { "No invitation output found in response" }
                serializerFormat.decodeFromJsonElement(outputJson)
            },
        )
    }

    suspend fun <T> executeQuery(query: Query<T>): T {
        return requester.executeQuery(query)
    }

    private fun requireValidAuth() {
        if (!authProvider.isAccountAuthorized()) {
            throw LightsparkAuthenticationException()
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun hashE164PhoneNumber(phoneNumber: String): String {
        val e164Regex = Regex("^\\+[1-9]\\d{1,14}\$")
        if (!e164Regex.matches(phoneNumber)) {
            throw IllegalArgumentException("Phone number must be in E164 format")
        }
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(phoneNumber.toByteArray())
        return digest.fold(StringBuilder()) { sb, it -> sb.append("%02x".format(it)) }.toString()
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun hashUmaIdentifier(identifier: String, signingPrivateKey: ByteArray): String {
        val now = getUtcDateTime()
        val input = identifier + "${now.monthNumber}-${now.year}" + signingPrivateKey.toHexString()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(input.toByteArray())
        return digest.fold(StringBuilder()) { sb, it -> sb.append("%02x".format(it)) }.toString()
    }

    fun getUtcDateTime(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.UTC)
    }

    fun setServerEnvironment(environment: ServerEnvironment, invalidateAuth: Boolean) {
        serverUrl = environment.graphQLUrl
        if (invalidateAuth) {
            authProvider = StubAuthProvider()
        }
        requester = Requester(nodeKeyCache, authProvider, serializerFormat, SCHEMA_ENDPOINT, serverUrl)
    }

    fun setBitcoinNetwork(network: BitcoinNetwork) {
        defaultBitcoinNetwork = network
    }
}

suspend fun <T> Query<T>.execute(client: LightsparkCoroutinesClient): T? = client.executeQuery(this)
