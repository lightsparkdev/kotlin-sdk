package com.lightspark.sdk

import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.core.auth.AuthProvider
import com.lightspark.sdk.core.auth.LightsparkAuthenticationException
import com.lightspark.sdk.core.crypto.SigningKeyLoader
import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.graphql.AccountDashboard
import com.lightspark.sdk.graphql.WalletDashboard
import com.lightspark.sdk.model.Account
import com.lightspark.sdk.model.BitcoinNetwork
import com.lightspark.sdk.model.ComplianceProvider
import com.lightspark.sdk.model.CreateApiTokenOutput
import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.model.FeeEstimate
import com.lightspark.sdk.model.IncomingPayment
import com.lightspark.sdk.model.Invoice
import com.lightspark.sdk.model.InvoiceData
import com.lightspark.sdk.model.InvoiceType
import com.lightspark.sdk.model.OutgoingPayment
import com.lightspark.sdk.model.PaymentDirection
import com.lightspark.sdk.model.RegionCode
import com.lightspark.sdk.model.ReleasePaymentPreimageOutput
import com.lightspark.sdk.model.RiskRating
import com.lightspark.sdk.model.TransactionStatus
import com.lightspark.sdk.model.UmaInvitation
import com.lightspark.sdk.model.WithdrawalMode
import com.lightspark.sdk.model.WithdrawalRequest
import java.util.concurrent.CompletableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future

/**
 * Main entry point for the Lightspark SDK using the Java Futures API.
 *
 * ```kotlin
 * // Initialize the client with account token info:
 * val lightsparkClient = LightsparkFuturesClient(ClientConfig(
 *     authProvider = AccountApiTokenAuthProvider(
 *         tokenId = "your-token-id"
 *         token = "your-secret-token"
 *     )
 * ))
 *
 * // An example API call fetching the dashboard info for the active account:
 * val dashboard = lightsparkClient.getFullAccountDashboard().await()
 * ```
 *
 * or in java:
 * ```java
 * // Initialize the client with account token info:
 * LightsparkFuturesClient lightsparkClient = new LightsparkFuturesClient(
 *   new ClientConfig()
 *      .setAuthProvider(new AccountApiTokenAuthProvider("your-token-id", "your-secret-token"))
 * );
 *
 * // An example API call fetching the dashboard info for the active account:
 * MultiNodeDashboard dashboard = lightsparkClient.getFullAccountDashboard().get(5, TimeUnit.SECONDS);
 * ```
 *
 * Note: This client object keeps a local cache in-memory, so a single instance should be reused
 * throughout the lifetime of your app.
 */
class LightsparkFuturesClient(config: ClientConfig) {
    private val coroutinesClient = LightsparkCoroutinesClient(config)
    private var defaultBitcoinNetwork: BitcoinNetwork = config.defaultBitcoinNetwork
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    /**
     * Override the auth token provider for this client to provide custom headers on all API calls.
     */
    fun setAuthProvider(authProvider: AuthProvider) {
        coroutinesClient.setAuthProvider(authProvider)
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
    @JvmOverloads
    fun getFullAccountDashboard(
        bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork,
        nodeIds: List<String>? = null,
    ): CompletableFuture<AccountDashboard> =
        coroutineScope.future { coroutinesClient.getFullAccountDashboard(bitcoinNetwork, nodeIds) }

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
    @JvmOverloads
    fun getSingleNodeDashboard(
        nodeId: String,
        numTransactions: Int = 20,
        bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork,
    ): CompletableFuture<WalletDashboard?> =
        coroutineScope.future { coroutinesClient.getSingleNodeDashboard(nodeId, numTransactions, bitcoinNetwork) }

    /**
     * Marks a payment preimage as released. To be used when the recipient has received the payment.
     *
     * @param invoiceId The invoice the preimage belongs to.
     * @param paymentPreimage The preimage to release.
     */
    fun releasePaymentPreimage(
        invoiceId: String,
        paymentPreimage: String
    ): CompletableFuture<ReleasePaymentPreimageOutput?> =
        coroutineScope.future {
            coroutinesClient.releasePaymentPreimage(
                invoiceId,
                paymentPreimage
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
    @JvmOverloads
    fun createInvoice(
        nodeId: String,
        amountMsats: Long,
        memo: String? = null,
        type: InvoiceType = InvoiceType.STANDARD,
        expirySecs: Int? = null,
        paymentHash: String? = null,
        preimageNonce: String? = null,
    ): CompletableFuture<Invoice> =
        coroutineScope.future {
            coroutinesClient.createInvoice(
                nodeId,
                amountMsats,
                memo,
                type,
                expirySecs,
                paymentHash,
                preimageNonce,
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
    @JvmOverloads
    fun createLnurlInvoice(
        nodeId: String,
        amountMsats: Long,
        metadata: String,
        expirySecs: Int? = null,
        paymentHash: String? = null,
        preimageNonce: String? = null,
    ): CompletableFuture<Invoice> =
        coroutineScope.future {
            coroutinesClient.createLnurlInvoice(
                nodeId,
                amountMsats,
                metadata,
                expirySecs,
                paymentHash,
                preimageNonce,
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
    @JvmOverloads
    @Throws(IllegalArgumentException::class)
    fun createUmaInvoice(
        nodeId: String,
        amountMsats: Long,
        metadata: String,
        expirySecs: Int? = null,
        signingPrivateKey: ByteArray? = null,
        receiverIdentifier: String? = null,
        paymentHash: String? = null,
        preimageNonce: String? = null,
    ): CompletableFuture<Invoice> =
        coroutineScope.future {
            coroutinesClient.createUmaInvoice(
                nodeId,
                amountMsats,
                metadata,
                expirySecs,
                signingPrivateKey,
                receiverIdentifier,
                paymentHash,
                preimageNonce,
            )
        }

    /**
     * Cancels an existing unpaid invoice and returns that invoice. Cancelled invoices cannot be paid.
     *
     * @param invoiceId The ID of the invoice to cancel.
     * @return The cancelled invoice as a [CompletableFuture].
     */
    fun cancelInvoice(invoiceId: String): CompletableFuture<Invoice> =
        coroutineScope.future { coroutinesClient.cancelInvoice(invoiceId) }

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
    fun payInvoice(
        nodeId: String,
        encodedInvoice: String,
        maxFeesMsats: Long,
        amountMsats: Long? = null,
        timeoutSecs: Int = 60,
        idempotencyKey: String? = null,
    ): CompletableFuture<OutgoingPayment> =
        coroutineScope.future {
            coroutinesClient.payInvoice(
                nodeId,
                encodedInvoice,
                maxFeesMsats,
                amountMsats,
                timeoutSecs,
                idempotencyKey,
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
    fun payUmaInvoice(
        nodeId: String,
        encodedInvoice: String,
        maxFeesMsats: Long,
        amountMsats: Long? = null,
        timeoutSecs: Int = 60,
        signingPrivateKey: ByteArray? = null,
        senderIdentifier: String? = null,
        idempotencyKey: String? = null,
    ): CompletableFuture<OutgoingPayment> =
        coroutineScope.future {
            coroutinesClient.payUmaInvoice(
                nodeId,
                encodedInvoice,
                maxFeesMsats,
                amountMsats,
                timeoutSecs,
                signingPrivateKey,
                senderIdentifier,
                idempotencyKey,
            )
        }

    /**
     * Decode a lightning invoice to get its details included payment amount, destination, etc.
     *
     * @param encodedInvoice An encoded string representation of the invoice to decode.
     * @return The decoded invoice details.
     */
    fun decodeInvoice(encodedInvoice: String): CompletableFuture<InvoiceData> =
        coroutineScope.future { coroutinesClient.decodeInvoice(encodedInvoice) }

    /**
     * Get the L1 fee estimate for a deposit or withdrawal.
     *
     * @param bitcoinNetwork The bitcoin network to use for the fee estimate. Defaults to the network set in the gradle
     *      project properties.
     * @return The fee estimate including a fast and minimum fee as [CurrencyAmount]s
     */
    @JvmOverloads
    fun getBitcoinFeeEstimate(bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork): CompletableFuture<FeeEstimate> =
        coroutineScope.future { coroutinesClient.getBitcoinFeeEstimate(bitcoinNetwork) }

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
    ): CompletableFuture<CurrencyAmount> =
        coroutineScope.future {
            coroutinesClient.getLightningFeeEstimateForNode(
                nodeId,
                destinationNodePublicKey,
                amountMsats,
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
    ): CompletableFuture<CurrencyAmount> =
        coroutineScope.future {
            coroutinesClient.getLightningFeeEstimateForInvoice(
                nodeId,
                encodedPaymentRequest,
                amountMsats,
            )
        }

    /**
     * Gets an estimated amount for the L1 withdrawal fees for the specified node, amount, and strategy.
     *
     * @param nodeId The node from where you want to send the payment.
     * @param amountSats The amount of funds to withdraw in SATOSHI. Use -1 to withdrawal all funds from this node.
     * @param mode The mode to use for the withdrawal. See `WithdrawalMode` for more information.
     * @returns An estimate of the fees that will be paid to withdraw funds for the node, amount, and strategy.
     */
    fun getWithdrawalFeeEstimate(
        nodeId: String,
        amountSats: Long,
        mode: WithdrawalMode,
    ): CompletableFuture<CurrencyAmount> =
        coroutineScope.future {
            coroutinesClient.getWithdrawalFeeEstimate(
                nodeId,
                amountSats,
                mode,
            )
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
    ): CompletableFuture<CreateApiTokenOutput> =
        coroutineScope.future { coroutinesClient.createApiToken(name, transact, testMode) }

    /**
     * Delete an API token for the current account.
     *
     * @param tokenId The ID of the token to delete.
     * @return True if the token was successfully deleted, false otherwise.
     */
    fun deleteApiToken(tokenId: String): CompletableFuture<Boolean> =
        coroutineScope.future { coroutinesClient.deleteApiToken(tokenId) }

    /**
     * Creates an L1 Bitcoin wallet address for a given node which can be used to deposit or withdraw funds.
     *
     * @param nodeId The ID of the node to create the wallet address for.
     * @return The newly created L1 wallet address.
     */
    fun createNodeWalletAddress(nodeId: String): CompletableFuture<String> =
        coroutineScope.future { coroutinesClient.createNodeWalletAddress(nodeId) }

    /**
     * @return The current account if one exists, or throws an exception if not logged into a valid account.
     */
    fun getCurrentAccount(): CompletableFuture<Account> =
        coroutineScope.future { coroutinesClient.getCurrentAccount() }

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
    fun fundNode(nodeId: String, amountSats: Long?, fundingAddress: String? = null): CompletableFuture<CurrencyAmount> =
        coroutineScope.future { coroutinesClient.fundNode(nodeId, amountSats, fundingAddress) }

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
    fun requestWithdrawal(
        nodeId: String,
        amountSats: Long,
        bitcoinAddress: String,
        mode: WithdrawalMode,
        idempotencyKey: String? = null,
    ): CompletableFuture<WithdrawalRequest> =
        coroutineScope.future {
            coroutinesClient.requestWithdrawal(
                nodeId,
                amountSats,
                bitcoinAddress,
                mode,
                idempotencyKey,
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
    fun sendPayment(
        payerNodeId: String,
        destinationPublicKey: String,
        amountMsats: Long,
        maxFeesMsats: Long,
        timeoutSecs: Int = 60,
        idempotencyKey: String? = null,
    ): CompletableFuture<OutgoingPayment> = coroutineScope.future {
        coroutinesClient.sendPayment(
            payerNodeId,
            destinationPublicKey,
            amountMsats,
            maxFeesMsats,
            timeoutSecs,
            idempotencyKey,
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
    @JvmOverloads
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun createTestModeInvoice(
        localNodeId: String,
        amountMsats: Long,
        memo: String? = null,
        invoiceType: InvoiceType? = null,
    ): CompletableFuture<String> = coroutineScope.future {
        coroutinesClient.createTestModeInvoice(
            localNodeId,
            amountMsats,
            memo,
            invoiceType,
        )
    }

    /**
     * In test mode, simulates a payment of a Lightning Invoice from another node.
     * This can only be used in test mode and should be used with invoices generated by [createInvoice].
     *
     * @param localNodeId The ID of the node that will receive the payment.
     * @param encodedInvoice The encoded invoice to pay.
     * @param amountMsats The amount to pay in milli-satoshis for 0-amount invoices. This should be null for non-zero
     *     amount invoices.
     */
    @JvmOverloads
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun createTestModePayment(
        localNodeId: String,
        encodedInvoice: String,
        amountMsats: Long? = null,
    ): CompletableFuture<IncomingPayment> = coroutineScope.future {
        coroutinesClient.createTestModePayment(
            localNodeId,
            encodedInvoice,
            amountMsats,
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
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun registerPayment(
        complianceProvider: ComplianceProvider,
        paymentId: String,
        nodePubKey: String,
        direction: PaymentDirection,
    ): CompletableFuture<String> = coroutineScope.future {
        coroutinesClient.registerPayment(
            complianceProvider,
            paymentId,
            nodePubKey,
            direction,
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
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun screenNode(
        complianceProvider: ComplianceProvider,
        nodePubKey: String,
    ): CompletableFuture<RiskRating> = coroutineScope.future {
        coroutinesClient.screenNode(complianceProvider, nodePubKey)
    }

    /**
     * Fetches the outgoing payments (if any) which have been made for a given invoice.
     *
     * @param encodedInvoice The encoded invoice to fetch the payments for.
     * @param transactionStatuses The transaction statuses to filter the payments by. If null, all payments will be
     *    returned.
     * @return The list of outgoing payments for the invoice.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun getOutgoingPaymentsForInvoice(
        encodedInvoice: String,
        transactionStatuses: List<TransactionStatus>? = null,
    ): CompletableFuture<List<OutgoingPayment>> = coroutineScope.future {
        coroutinesClient.getOutgoingPaymentsForInvoice(encodedInvoice, transactionStatuses)
    }

    /**
     * fetch outgoing payments for a given payment hash
     *
     * @param paymentHash the payment hash of the invoice for which to fetch the outgoing payments
     * @param transactionStatuses the transaction statuses to filter the payments by.  If null, all payments will be returned.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun getOutgoingPaymentsForPaymentHash(
        paymentHash: String,
        transactionStatuses: List<TransactionStatus>? = null,
    ): CompletableFuture<List<OutgoingPayment>> = coroutineScope.future {
        coroutinesClient.getOutgoingPaymentForPaymentHash(paymentHash, transactionStatuses)
    }

    /**
     * Fetch outgoing payment for a given idempotency key
     *
     * @param idempotencyKey The idempotency key used when creating the payment.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun getOutgoingPaymentForIdempotencyKey(
        idempotencyKey: String,
    ): CompletableFuture<OutgoingPayment?> = coroutineScope.future {
        coroutinesClient.getOutgoingPaymentForIdempotencyKey(idempotencyKey)
    }

    /**
     * fetch invoice for a given payments hash
     *
     * @param paymentHash the payment hash of the invoice for which to fetch the outgoing payments
     * @param transactionStatuses the transaction statuses to filter the payments by.  If null, all payments will be returned.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun getInvoiceForPaymentHash(
        paymentHash: String,
    ): CompletableFuture<Invoice> = coroutineScope.future {
        coroutinesClient.getInvoiceForPaymentHash(paymentHash)
    }

    /**
     * Fetch incoming payments for a given payment hash.
     *
     * @param paymentHash The payment hash of the invoice for which to fetch the incoming payments.
     * @param transactionStatuses The transaction statuses to filter the payments by. If null, all payments will be
     *   returned.
     * @return The list of incoming payments for the payment hash.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun getIncomingPaymentsForPaymentHash(
        paymentHash: String,
        transactionStatuses: List<TransactionStatus>? = null,
    ): CompletableFuture<List<IncomingPayment>> = coroutineScope.future {
        coroutinesClient.getIncomingPaymentsForPaymentHash(paymentHash, transactionStatuses)
    }

    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun getIncomingPaymentsForInvoice(
        invoiceId: String,
        transactionStatuses: List<TransactionStatus>? = null,
    ): CompletableFuture<List<IncomingPayment>> = coroutineScope.future {
        coroutinesClient.getIncomingPaymentsForInvoice(invoiceId, transactionStatuses)
    }

    /**
     * Creates an UMA invitation. If you are part of the incentive program you should use
     * [createUmaInvitationWithIncentives].
     *
     * @param inviterUma The UMA of the inviter.
     * @return The invitation that was created.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun createUmaInvitation(inviterUma: String): CompletableFuture<UmaInvitation> = coroutineScope.future {
        coroutinesClient.createUmaInvitation(inviterUma)
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
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class, IllegalArgumentException::class)
    fun createUmaInvitationWithIncentives(
        inviterUma: String,
        inviterPhoneNumberE164: String,
        inviterRegionCode: RegionCode,
    ): CompletableFuture<UmaInvitation> = coroutineScope.future {
        coroutinesClient.createUmaInvitationWithIncentives(inviterUma, inviterPhoneNumberE164, inviterRegionCode)
    }

    /**
     * Claims an UMA invitation. If you are part of the incentive program, you should use
     * [claimUmaInvitationWithIncentives].
     *
     * @param invitationCode The invitation code to claim.
     * @param inviteeUma The UMA of the invitee.
     * @returns The invitation that was claimed.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun claimUmaInvitation(invitationCode: String, inviteeUma: String): CompletableFuture<UmaInvitation> =
        coroutineScope.future {
            coroutinesClient.claimUmaInvitation(invitationCode, inviteeUma)
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
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class, IllegalArgumentException::class)
    fun claimUmaInvitationWithIncentives(
        invitationCode: String,
        inviteeUma: String,
        inviteePhoneNumberE164: String,
        inviteeRegionCode: RegionCode,
    ): CompletableFuture<UmaInvitation> = coroutineScope.future {
        coroutinesClient.claimUmaInvitationWithIncentives(
            invitationCode,
            inviteeUma,
            inviteePhoneNumberE164,
            inviteeRegionCode,
        )
    }

    /**
     * Fetches an UMA invitation by its invitation code.
     *
     * @param invitationCode The code of the invitation to fetch.
     * @returns The invitation with the given code, or null if no invitation exists with that code.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class)
    fun fetchUmaInvitation(invitationCode: String): CompletableFuture<UmaInvitation> = coroutineScope.future {
        coroutinesClient.fetchUmaInvitation(invitationCode)
    }

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
    ): CompletableFuture<Boolean> =
        coroutineScope.future { coroutinesClient.recoverNodeSigningKey(nodeId, nodePassword) }

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
    fun loadNodeSigningKey(nodeId: String, signingKeyLoader: SigningKeyLoader) = coroutineScope.future {
        coroutinesClient.loadNodeSigningKey(nodeId, signingKeyLoader)
    }

    fun <T> executeQuery(query: Query<T>): CompletableFuture<T> =
        coroutineScope.future { coroutinesClient.executeQuery(query) }

    fun setBitcoinNetwork(network: BitcoinNetwork) {
        defaultBitcoinNetwork = network
    }
}

fun <T> Query<T>.execute(client: LightsparkFuturesClient): CompletableFuture<T> = client.executeQuery(this)
