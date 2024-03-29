@file:JvmName("-LightsparkKotlinWalletClient")

package com.lightspark.sdk.wallet

import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.core.auth.*
import com.lightspark.sdk.core.crypto.AliasedRsaSigningKeyLoader
import com.lightspark.sdk.core.crypto.NodeKeyCache
import com.lightspark.sdk.core.crypto.RawRsaSigningKeyLoader
import com.lightspark.sdk.core.crypto.SigningKeyLoader
import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.core.requester.Requester
import com.lightspark.sdk.core.requester.ServerEnvironment
import com.lightspark.sdk.wallet.auth.jwt.CustomJwtAuthProvider
import com.lightspark.sdk.wallet.auth.jwt.JwtStorage
import com.lightspark.sdk.wallet.auth.jwt.JwtTokenInfo
import com.lightspark.sdk.wallet.graphql.*
import com.lightspark.sdk.wallet.model.*
import com.lightspark.sdk.wallet.util.serializerFormat
import saschpe.kase64.base64DecodedBytes
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.transformWhile
import kotlinx.serialization.json.*

private const val WALLET_NODE_ID_KEY = "wallet_node_id"
private const val SCHEMA_ENDPOINT = "graphql/wallet/2023-05-05"

/**
 * Main entry point for the Lightspark Wallet SDK.
 *
 * ```kotlin
 * // Initialize the client with account token info:
 * val oAuthHelper = OauthHelper(applicationContext)
 * val lightsparkClient = LightsparkCoroutinesWalletClient(ClientConfig(
 *     authProvider = OAuthProvider(oAuthHelper)
 * ))
 *
 * // An example API call fetching the dashboard info for the active account:
 * val dashboard = lightsparkClient.getWalletDashboard()
 * ```
 *
 * Note: This client object keeps a local cache in-memory, so a single instance should be reused
 * throughout the lifetime of your app.
 */
class LightsparkCoroutinesWalletClient private constructor(
    private var authProvider: AuthProvider,
    private var serverUrl: String = ServerEnvironment.PROD.graphQLUrl,
    private val nodeKeyCache: NodeKeyCache = NodeKeyCache(),
) {
    internal var requester = Requester(nodeKeyCache, authProvider, serializerFormat, SCHEMA_ENDPOINT, serverUrl)

    constructor(config: ClientConfig) : this(
        config.authProvider ?: StubAuthProvider(),
        config.serverUrl,
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
     * Login using the Custom JWT authentication scheme described in our documentation.
     *
     * Note: When using this method, you are responsible for refreshing the JWT token before or when it expires. If the
     * token expires, the client will throw a [LightsparkAuthenticationException] on the next API call which requires
     * valid authentication. Then you'll need to call this method again to get a new token.
     *
     * @param accountId The account ID to login with. This is specific to your company's account.
     * @param jwt The JWT to use for authentication of this user.
     * @param storage A [JwtStorage] implementation that will store the new JWT token info.
     * @return The output of the login operation, including the access token, expiration time, and wallet info.
     * @throws LightsparkException if the login fails.
     */
    @Throws(LightsparkException::class, CancellationException::class)
    suspend fun loginWithJWT(accountId: String, jwt: String, storage: JwtStorage): LoginWithJWTOutput {
        val output = executeQuery(
            Query(
                LoginWithJWT,
                {
                    add("account_id", accountId)
                    add("jwt", jwt)
                },
            ) {
                val jwtJson = it["login_with_jwt"] ?: throw LightsparkException(
                    "Failed to complete jwt auth. Please double-check your account configuration.",
                    LightsparkErrorCode.JWT_AUTH_ERROR,
                )
                serializerFormat.decodeFromJsonElement<LoginWithJWTOutput>(jwtJson)
            },
        )

        val authProvider = CustomJwtAuthProvider(storage)
        authProvider.setTokenInfo(JwtTokenInfo(output.accessToken, output.validUntil))
        setAuthProvider(authProvider)

        return output
    }

    /**
     * Deploys a wallet in the Lightspark infrastructure. This is an asynchronous operation, the caller should then poll
     * the wallet frequently (or subscribe to its modifications). When this process is over, the Wallet status will
     * change to `DEPLOYED` (or `FAILED`).
     *
     * @return The wallet that was deployed.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun deployWallet(): Wallet {
        requireValidAuth()
        return executeQuery(
            Query(DeployWallet, {}) {
                serializerFormat.decodeFromJsonElement<DeployWalletOutput>(it["deploy_wallet"]!!).wallet
            },
        )
    }

    /**
     * Deploys a wallet in the Lightspark infrastructure and triggers updates as state changes.
     * This is an asynchronous operation, which will continue sending the wallet state updates until
     * the Wallet status changes to `DEPLOYED` (or `FAILED`).
     *
     * @return The a flow which emits when wallet status changes.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun deployWalletAndAwaitDeployed(): Flow<Wallet> {
        val initialWallet = deployWallet()
        if (initialWallet.status == WalletStatus.DEPLOYED) {
            return flowOf(initialWallet)
        }
        return awaitWalletStatus(setOf(WalletStatus.DEPLOYED, WalletStatus.FAILED))
    }

    private fun awaitWalletStatus(statuses: Set<WalletStatus>): Flow<Wallet> {
        return requester.executeAsSubscription(
            Query(
                CurrentWalletSubscription,
                {},
            ) {
                val walletJson = it["current_wallet"] ?: return@Query null
                serializerFormat.decodeFromJsonElement<Wallet>(walletJson)
            },
        ).mapNotNull {
            when (it) {
                is Lce.Content -> it.data
                else -> null
            }
        }.transformWhile {
            emit(it)
            it.status !in statuses
        }
    }

    /**
     * Initializes a wallet in the Lightspark infrastructure and syncs it to the Bitcoin network. This is an
     * asynchronous operation, the caller should then poll the wallet frequently (or subscribe to its modifications).
     * When this process is over, the Wallet status will change to `READY` (or `FAILED`).
     *
     * @param keyType The type of key to use for the wallet.
     * @param signingPublicKey The base64-encoded public key to use for signing transactions.
     * @param signingPrivateKey The base64-encoded RSA private key to use for signing transactions. This will not leave
     *     the device. It is only used for signing this request.
     * @return The wallet that was initialized.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun initializeWallet(keyType: KeyType, signingPublicKey: String, signingPrivateKey: String): Wallet {
        requireValidAuth()
        loadWalletSigningKey(RawRsaSigningKeyLoader(signingPrivateKey.base64DecodedBytes))
        return executeQuery(
            Query(
                InitializeWallet,
                {
                    add("key_type", serializerFormat.encodeToJsonElement(keyType))
                    add("signing_public_key", signingPublicKey)
                },
                signingNodeId = WALLET_NODE_ID_KEY,
            ) {
                serializerFormat.decodeFromJsonElement<InitializeWalletOutput>(it["initialize_wallet"]!!).wallet
            },
        )
    }

    /**
     * Initializes a wallet in the Lightspark infrastructure and syncs it to the Bitcoin network and triggers updates
     * as state changes. This is an asynchronous operation, which will continue sending the wallet state updates until
     * the Wallet status changes to `READY` (or `FAILED`).
     *
     * @param keyType The type of key to use for the wallet.
     * @param signingPublicKey The base64-encoded public key to use for signing transactions.
     * @param signingPrivateKey The base64-encoded private key to use for signing transactions. This will not leave the
     *    device. It is only used for signing this request.
     * @return A flow of Wallet updates.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    suspend fun initializeWalletAndWaitForInitialized(
        keyType: KeyType,
        signingPublicKey: String,
        signingPrivateKey: String,
    ): Flow<Wallet> {
        val initialWallet = initializeWallet(keyType, signingPublicKey, signingPrivateKey)
        if (initialWallet.status == WalletStatus.READY) {
            return flowOf(initialWallet)
        }
        return awaitWalletStatus(setOf(WalletStatus.READY, WalletStatus.FAILED))
    }

    /**
     * Removes the wallet from Lightspark infrastructure. It won't be connected to the Lightning network anymore and
     * its funds won't be accessible outside of the Funds Recovery Kit process.
     *
     * @return The wallet that was terminated.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun terminateWallet(): Wallet {
        requireValidAuth()
        return executeQuery(
            Query(TerminateWallet, {}) {
                serializerFormat.decodeFromJsonElement<TerminateWalletOutput>(it["terminate_wallet"]!!).wallet
            },
        )
    }

    /**
     * Removes the wallet from Lightspark infrastructure. It won't be connected to the Lightning network anymore and
     * its funds won't be accessible outside of the Funds Recovery Kit process.
     *
     * This waits for the wallet to be either `TERMINATED`or `FAILED`. It will periodically fire updates until then.
     *
     * @return The wallet updates until it is terminated.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    suspend fun terminateAndWaitForTerminated(): Flow<Wallet> {
        val initialWallet = terminateWallet()
        if (initialWallet.status == WalletStatus.TERMINATED) {
            return flowOf(initialWallet)
        }
        return awaitWalletStatus(setOf(WalletStatus.TERMINATED, WalletStatus.FAILED))
    }

    /**
     * Get the dashboard overview for a Lightning wallet. Includes balance info and
     * the most recent transactions and payment requests.
     *
     * @param numTransactions The max number of recent transactions to fetch. Defaults to 20.
     * @param numPaymentRequests The max number of recent payment requests to fetch. Defaults to 20.
     * @return The dashboard overview for the wallet, including balance and recent transactions and payment requests.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun getWalletDashboard(
        numTransactions: Int = 20,
        numPaymentRequests: Int = 20,
    ): WalletDashboard? {
        requireValidAuth()
        return executeQuery(
            Query(
                WalletDashboardQuery,
                {
                    add("numTransactions", numTransactions)
                    add("numPaymentRequests", numPaymentRequests)
                },
            ) { jsonObj ->
                val walletJson = jsonObj["current_wallet"]?.jsonObject ?: return@Query null

                return@Query WalletDashboard(
                    walletJson["id"]!!.jsonPrimitive.content,
                    walletJson["status"]!!.jsonPrimitive.content.let {
                        WalletStatus.valueOf(it)
                    },
                    walletJson["balances"]?.let { serializerFormat.decodeFromJsonElement(it) },
                    serializerFormat.decodeFromJsonElement(walletJson["recent_transactions"]!!),
                    serializerFormat.decodeFromJsonElement(walletJson["payment_requests"]!!),
                )
            },
        )
    }

    /**
     * Creates a lightning invoice from the current wallet.
     *
     * Test mode note: You can simulate a payment of this invoice in test move using [createTestModePayment].
     *
     * @param amountMsats The amount of the invoice in milli-satoshis.
     * @param memo Optional memo to include in the invoice.
     * @param type The type of invoice to create. Defaults to [InvoiceType.STANDARD].
     * @param expirySecs The number of seconds until the invoice expires. Defaults to 1 day.
     * @return The created invoice.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun createInvoice(
        amountMsats: Long,
        memo: String? = null,
        type: InvoiceType = InvoiceType.STANDARD,
        expirySecs: Int? = null,
    ): Invoice {
        requireValidAuth()
        return executeQuery(
            Query(
                CreateInvoiceMutation,
                {
                    add("amountMsats", amountMsats)
                    memo?.let { add("memo", memo) }
                    add("type", serializerFormat.encodeToJsonElement(type))
                    expirySecs?.let { add("expirySecs", expirySecs) }
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
     * Cancels an existing unpaid invoice and returns that invoice. Cancelled invoices cannot be paid.
     *
     * @param invoiceId The ID of the invoice to cancel.
     * @return The cancelled invoice.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
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
     * Pay a lightning invoice from the current wallet.
     *
     * Note: This call will fail if the wallet is not unlocked yet via [loadWalletSigningKey]. You must successfully
     * unlock the wallet before calling this function.
     *
     * Test mode note: For test mode, you can use the [createTestModeInvoice] function to create an invoice you can
     * pay in test mode.
     *
     * @param encodedInvoice An encoded string representation of the invoice to pay.
     * @param maxFeesMsats The maximum fees to pay in milli-satoshis. You must pass a value.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param amountMsats The amount to pay in milli-satoshis. Defaults to the full amount of the invoice.
     * @param timeoutSecs The number of seconds to wait for the payment to complete. Defaults to 60.
     * @return The payment details.
     * @throws LightsparkException If the payment fails or if the wallet is locked.
     */
    @JvmOverloads
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun payInvoice(
        encodedInvoice: String,
        maxFeesMsats: Long,
        amountMsats: Long? = null,
        timeoutSecs: Int = 60,
    ): OutgoingPayment {
        requireValidAuth()
        requireWalletUnlocked()
        return executeQuery(
            Query(
                PayInvoiceMutation,
                {
                    add("encoded_invoice", encodedInvoice)
                    add("timeout_secs", timeoutSecs)
                    add("maximum_fees_msats", maxFeesMsats)
                    amountMsats?.let { add("amount_msats", amountMsats) }
                },
                signingNodeId = WALLET_NODE_ID_KEY,
            ) {
                val paymentJson =
                    requireNotNull(it["pay_invoice"]?.jsonObject?.get("payment")) { "No payment found in response" }
                serializerFormat.decodeFromJsonElement(paymentJson)
            },
        )
    }

    /**
     * Pay a lightning invoice from the current wallet.
     * Waits for the payment to complete successfully or fail, emitting current payment object until it completes.
     *
     * Note: This call will fail if the wallet is not unlocked yet via [loadWalletSigningKey]. You must successfully
     * unlock the wallet before calling this function.
     *
     * @param encodedInvoice An encoded string representation of the invoice to pay.
     * @param maxFeesMsats The maximum fees to pay in milli-satoshis. You must pass a value.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param amountMsats The amount to pay in milli-satoshis. Defaults to the full amount of the invoice.
     * @param timeoutSecs The number of seconds to wait for the payment to complete. Defaults to 60.
     * @return A `Flow<OutgoingPayment>` that will emit the payment details periodically until the payment completes or
     *     fails.
     * @throws LightsparkException If the payment fails or if the wallet is locked.
     */
    suspend fun payInvoiceAndAwaitCompletion(
        encodedInvoice: String,
        maxFeesMsats: Long,
        amountMsats: Long? = null,
        timeoutSecs: Int = 60,
    ): Flow<OutgoingPayment> {
        val payment = payInvoice(encodedInvoice, maxFeesMsats, amountMsats, timeoutSecs)
        val completedStatuses = setOf(TransactionStatus.SUCCESS, TransactionStatus.FAILED, TransactionStatus.CANCELLED)
        if (payment.status in completedStatuses) {
            return flowOf(payment)
        }
        return awaitOutgoingPaymentStatus(payment.id, completedStatuses)
    }

    private fun awaitOutgoingPaymentStatus(
        transactionId: String,
        statuses: Set<TransactionStatus>,
    ): Flow<OutgoingPayment> {
        return requester.executeAsSubscription(OutgoingPayment.getOutgoingPaymentQuery(transactionId)).mapNotNull {
            when (it) {
                is Lce.Content -> it.data
                else -> null
            }
        }.transformWhile {
            emit(it)
            it.status !in statuses
        }
    }

    /**
     * Decode a lightning invoice to get its details included payment amount, destination, etc.
     *
     * @param encodedInvoice An encoded string representation of the invoice to decode.
     * @return The decoded invoice details.
     */
    @Throws(CancellationException::class)
    suspend fun decodeInvoice(encodedInvoice: String): InvoiceData {
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
     * @return The fee estimate including a fast and minimum fee as [CurrencyAmount]s
     */
    @Throws(CancellationException::class)
    suspend fun getBitcoinFeeEstimate(): FeeEstimate {
        return executeQuery(
            Query(BitcoinFeeEstimateQuery, {}) {
                val feeEstimateJson =
                    requireNotNull(it["bitcoin_fee_estimate"]) { "No fee estimate found in response" }
                serializerFormat.decodeFromJsonElement(feeEstimateJson)
            },
        )
    }

    /**
     * Gets an estimate of the fees that will be paid for a Lightning invoice.
     *
     * @param encodedPaymentRequest The invoice you want to pay (as defined by the BOLT11 standard).
     * @param amountMsats If the invoice does not specify a payment amount, then the amount that you wish to pay,
     *     expressed in msats.
     * @returns An estimate of the fees that will be paid for a Lightning invoice.
     * @throws LightsparkAuthenticationException If the user is not authenticated.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun getLightningFeeEstimateForInvoice(
        encodedPaymentRequest: String,
        amountMsats: Long? = null,
    ): CurrencyAmount {
        requireValidAuth()
        return executeQuery(
            Query(
                LightningFeeEstimateForInvoiceQuery,
                {
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
     * @param destinationNodePublicKey The public key of the node that you want to pay.
     * @param amountMsats The payment amount expressed in msats.
     * @returns An estimate of the fees that will be paid to send a payment to another Lightning node.
     * @throws LightsparkAuthenticationException If the user is not authenticated.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun getLightningFeeEstimateForNode(
        destinationNodePublicKey: String,
        amountMsats: Long,
    ): CurrencyAmount {
        requireValidAuth()
        return executeQuery(
            Query(
                LightningFeeEstimateForNodeQuery,
                {
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
     * Unlocks the wallet for use with the SDK for the current application session. This function
     * must be called before any other functions that require wallet signing keys, including [payInvoice].
     *
     * It is the responsibility of the application to ensure that the key is valid and that it is the correct key for
     * the wallet. Otherwise signed requests will fail.
     *
     * @param signingKeyLoader A [SigningKeyLoader] implementation that will load the wallet's private signing key.
     * @throws LightsparkAuthenticationException if the user is not authenticated.
     */
    @Throws(LightsparkAuthenticationException::class)
    suspend fun loadWalletSigningKey(signingKeyLoader: SigningKeyLoader) {
        requireValidAuth()
        nodeKeyCache[WALLET_NODE_ID_KEY] = signingKeyLoader.loadSigningKey(requester)
    }

    /**
     * Unlocks the wallet for use with the SDK for the current application session by specifying a key alias in the
     * KeyStore where the wallet's key is stored.
     *
     * This function or [loadWalletSigningKey] must be called before any other functions that require wallet signing
     * keys, including [payInvoice].
     *
     * This function is intended for use in cases where the wallet's private signing key is already saved by the
     * application outside of the SDK. It is the responsibility of the application to ensure that the key is valid and
     * that it is the correct key for the wallet. Otherwise signed requests will fail.
     *
     * @param signingKeyAlias The key alias in the KeyStore of the wallet's private signing key.
     * @throws LightsparkAuthenticationException if the user is not authenticated.
     */
    @Throws(LightsparkAuthenticationException::class)
    suspend fun loadWalletSigningKeyAlias(signingKeyAlias: String) {
        loadWalletSigningKey(AliasedRsaSigningKeyLoader(signingKeyAlias))
    }

    /**
     * Creates an L1 Bitcoin wallet address which can be used to deposit or withdraw funds from the Lightning wallet.
     *
     * @return The newly created L1 wallet address.
     * @throws LightsparkException if there's no valid auth.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun createBitcoinFundingAddress(): String {
        requireValidAuth()
        return executeQuery(
            Query(CreateBitcoinFundingAddress, {}) {
                val addressString =
                    requireNotNull(it["create_bitcoin_funding_address"]?.jsonObject?.get("bitcoin_address")) {
                        "No address found in response"
                    }
                addressString.jsonPrimitive.content
            },
        )
    }

    /**
     * @return The current wallet if one exists, null otherwise.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun getCurrentWallet(): Wallet? {
        requireValidAuth()
        return executeQuery(
            Query(
                CurrentWalletQuery,
                {},
            ) {
                val accountJson = it["current_wallet"] ?: return@Query null
                serializerFormat.decodeFromJsonElement(accountJson)
            },
        )
    }

    /**
     * Withdraws funds from the account and sends it to the requested bitcoin address.
     *
     * The process is asynchronous and may take up to a few minutes. You can check the progress by polling the
     * `WithdrawalRequest` that is created, or by subscribing to a webhook.
     *
     * @param amountSats The amount of funds to withdraw in SATOSHI.
     * @param bitcoinAddress The Bitcoin address to withdraw funds to.
     * @throws LightsparkException if the withdrawal failed or if the wallet is locked.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun requestWithdrawal(
        amountSats: Long,
        bitcoinAddress: String,
    ): WithdrawalRequest {
        requireValidAuth()
        requireWalletUnlocked()
        return executeQuery(
            Query(
                RequestWithdrawalMutation,
                {
                    add("amount_sats", amountSats)
                    add("bitcoin_address", bitcoinAddress)
                },
                signingNodeId = WALLET_NODE_ID_KEY,
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
     * @param destinationPublicKey The public key of the destination node.
     * @param amountMsats The amount to pay in milli-satoshis.
     * @param maxFeesMsats The maximum amount of fees that you want to pay for this payment to be sent.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param timeoutSecs The timeout in seconds that we will try to make the payment.
     * @return An `OutgoingPayment` object if the payment was successful, or throws if the payment failed.
     * @throws LightsparkException if the payment failed or if the wallet is locked.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun sendPayment(
        destinationPublicKey: String,
        amountMsats: Long,
        maxFeesMsats: Long,
        timeoutSecs: Int = 60,
    ): OutgoingPayment {
        requireValidAuth()
        requireWalletUnlocked()
        return executeQuery(
            Query(
                SendPaymentMutation,
                {
                    add("destination_public_key", destinationPublicKey)
                    add("amount_msats", amountMsats)
                    add("timeout_secs", timeoutSecs)
                    add("maximum_fees_msats", maxFeesMsats)
                },
                signingNodeId = WALLET_NODE_ID_KEY,
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
     * Sends a payment directly to a node on the Lightning Network through the public key of the node without an invoice.
     * Waits for the payment to complete successfully or fail, emitting current payment object until it completes.
     *
     * @param destinationPublicKey The public key of the destination node.
     * @param amountMsats The amount to pay in milli-satoshis.
     * @param maxFeesMsats The maximum amount of fees that you want to pay for this payment to be sent.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param timeoutSecs The timeout in seconds that we will try to make the payment.
     * @return A `Flow<OutgoingPayment>` which emits payment object until it completes or fails.
     * @throws LightsparkException if the payment failed or if the wallet is locked.
     */
    suspend fun sendPaymentAndAwaitCompletion(
        destinationPublicKey: String,
        amountMsats: Long,
        maxFeesMsats: Long,
        timeoutSecs: Int = 60,
    ): Flow<OutgoingPayment> {
        val payment = sendPayment(destinationPublicKey, amountMsats, maxFeesMsats, timeoutSecs)
        val completedStatuses = setOf(TransactionStatus.SUCCESS, TransactionStatus.FAILED, TransactionStatus.CANCELLED)
        if (payment.status in completedStatuses) {
            return flowOf(payment)
        }
        return awaitOutgoingPaymentStatus(payment.id, completedStatuses)
    }

    /**
     * In test mode, generates a Lightning Invoice which can be paid by a local node.
     * This call is only valid in test mode. You can then pay the invoice using [payInvoice].
     *
     * @param amountMsats The amount to pay in milli-satoshis.
     * @param memo An optional memo to attach to the invoice.
     * @param invoiceType The type of invoice to create.
     */
    suspend fun createTestModeInvoice(
        amountMsats: Long,
        memo: String? = null,
        invoiceType: InvoiceType? = null,
    ): String {
        requireValidAuth()
        return executeQuery(
            Query(
                CreateTestModeInvoice,
                {
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
     * This can only be used in test mode and should be used with invoices generated by [createInvoice].
     *
     * @param encodedInvoice The encoded invoice to pay.
     * @param amountMsats The amount to pay in milli-satoshis for 0-amount invoices. This should be null for non-zero
     *     amount invoices.
     */
    suspend fun createTestModePayment(
        encodedInvoice: String,
        amountMsats: Long? = null,
    ): IncomingPayment {
        requireValidAuth()
        return executeQuery(
            Query(
                CreateTestModePayment,
                {
                    add("encoded_invoice", encodedInvoice)
                    amountMsats?.let { add("amount_msats", amountMsats) }
                },
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
     * Executes a raw graphql query against the server.
     */
    suspend fun <T> executeQuery(query: Query<T>): T {
        return requester.executeQuery(query)
    }

    @Throws(LightsparkAuthenticationException::class)
    internal fun requireValidAuth() {
        if (!authProvider.isAccountAuthorized()) {
            throw LightsparkAuthenticationException()
        }
    }

    @Throws(LightsparkException::class)
    private fun requireWalletUnlocked() {
        if (!nodeKeyCache.contains(WALLET_NODE_ID_KEY)) {
            throw LightsparkException(
                "Wallet is locked. Call loadWalletSigningKey before calling this function.",
                LightsparkErrorCode.WALLET_LOCKED,
            )
        }
    }

    /**
     * @return A [Flow] that emits true if the wallet is unlocked or false if it is locked.
     */
    fun observeWalletUnlocked() =
        nodeKeyCache.observeCachedNodeIds().map { it.contains(WALLET_NODE_ID_KEY) }

    /**
     * @return True if the wallet is unlocked or false if it is locked.
     */
    fun isWalletUnlocked() = nodeKeyCache.contains(WALLET_NODE_ID_KEY)

    fun setServerEnvironment(environment: ServerEnvironment, invalidateAuth: Boolean) {
        serverUrl = environment.graphQLUrl
        if (invalidateAuth) {
            authProvider = StubAuthProvider()
        }
        requester = Requester(nodeKeyCache, authProvider, serializerFormat, SCHEMA_ENDPOINT, serverUrl)
    }
}

suspend fun <T> Query<T>.execute(client: LightsparkCoroutinesWalletClient): T? = client.executeQuery(this)
