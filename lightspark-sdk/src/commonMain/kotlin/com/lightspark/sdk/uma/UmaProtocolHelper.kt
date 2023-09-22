@file:OptIn(ExperimentalStdlibApi::class)

package com.lightspark.sdk.uma

import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import com.lightspark.sdk.crypto.Secp256k1
import com.lightspark.sdk.crypto.internal.CryptoException
import com.lightspark.sdk.model.Invoice
import com.lightspark.sdk.util.serializerFormat
import java.security.MessageDigest
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future
import kotlin.random.Random
import kotlin.random.nextULong
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

/**
 * A helper class for interacting with the UMA protocol. It provides methods for creating and verifying UMA requests
 * and responses.
 */
class UmaProtocolHelper @JvmOverloads constructor(
    private val publicKeyCache: PublicKeyCache = InMemoryPublicKeyCache(),
    private val umaRequester: UmaRequester = KtorUmaRequester(),
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    /**
     * Fetches the public keys of a VASP or returns the cached keys if they are still valid.
     *
     * In Kotlin, prefer using [fetchPublicKeysForVasp] instead.
     *
     * @param vaspDomain The domain of the VASP whose public keys are being fetched.
     * @return The [PubKeyResponse] containing the public keys of the VASP.
     */
    @Throws(Exception::class)
    fun fetchPublicKeysForVaspFuture(vaspDomain: String): Future<PubKeyResponse> = coroutineScope.future {
        fetchPublicKeysForVasp(vaspDomain)
    }

    /**
     * Fetches the public keys of a VASP or returns the cached keys if they are still valid.
     *
     * This method is synchronous and should only be used in cases where the caller is already on a background thread.
     * In Kotlin, prefer using [fetchPublicKeysForVasp] instead.
     *
     * @param vaspDomain The domain of the VASP whose public keys are being fetched.
     * @return The [PubKeyResponse] containing the public keys of the VASP.
     */
    @Throws(Exception::class)
    fun fetchPublicKeysForVaspSync(vaspDomain: String): PubKeyResponse = runBlocking {
        fetchPublicKeysForVasp(vaspDomain)
    }

    /**
     * Fetches the public keys of a VASP or returns the cached keys if they are still valid.
     *
     * @param vaspDomain The domain of the VASP whose public keys are being fetched.
     * @return The [PubKeyResponse] containing the public keys of the VASP.
     */
    @JvmName("KotlinOnly-fetchPublicKeysForVaspSuspended")
    suspend fun fetchPublicKeysForVasp(vaspDomain: String): PubKeyResponse {
        val cached = publicKeyCache.getPublicKeysForVasp(vaspDomain)
        if (cached != null) {
            return cached
        }

        val scheme = if (vaspDomain.startsWith("localhost:")) "http" else "https"
        val response = umaRequester.makeGetRequest("$scheme://$vaspDomain/.well-known/lnurlpubkey")
        val pubKeyResponse = serializerFormat.decodeFromString<PubKeyResponse>(response)
        publicKeyCache.addPublicKeysForVasp(vaspDomain, pubKeyResponse)
        return pubKeyResponse
    }

    private fun generateNonce(): String {
        return Random.nextULong().toString()
    }

    /**
     * Creates a signed UMA [LnurlpRequest].
     *
     * @param signingPrivateKey The signing private key of the VASP that is sending the payment. This will be used to
     *     sign the request.
     * @param receiverAddress The address of the user at the receiving VASP that is receiving the payment
     *     (i.e. $bob@vasp2.com).
     * @param senderVaspDomain The domain of the VASP that is sending the payment. It will be used by the receiving VASP
     *     to fetch the public keys of the sending VASP.
     * @param isSubjectToTravelRule Indicates whether the sending VASP is a financial institution that requires travel rule
     *     information.
     * @param umaVersion The version of the UMA protocol that the sending VASP prefers to use for this transaction. This
     *     parameter should only be overridden in cases where the receiving VASP does not support the default version
     *     for this SDK. For the version negotiation flow, see
     *     https://static.swimlanes.io/87f5d188e080cb8e0494e46f80f2ae74.png
     */
    @JvmOverloads
    @Throws(CryptoException::class)
    fun getSignedLnurlpRequestUrl(
        signingPrivateKey: ByteArray,
        receiverAddress: String,
        senderVaspDomain: String,
        isSubjectToTravelRule: Boolean,
        umaVersion: String = UMA_VERSION_STRING,
    ): String {
        val nonce = generateNonce()
        val timestamp = System.currentTimeMillis() / 1000
        val unsignedRequest = LnurlpRequest(
            receiverAddress = receiverAddress,
            vaspDomain = senderVaspDomain,
            isSubjectToTravelRule = isSubjectToTravelRule,
            nonce = nonce,
            timestamp = timestamp,
            signature = "",
            umaVersion = umaVersion,
        )
        val signature = signPayload(unsignedRequest.signablePayload(), signingPrivateKey)
        return unsignedRequest.signedWith(signature).encodeToUrl()
    }

    /**
     * @return true if the given URL is a valid UMA Lnurlp query, false otherwise.
     */
    fun isUmaLnurlpQuery(url: String): Boolean {
        return try {
            parseLnurlpRequest(url)
            true
        } catch (e: UnsupportedVersionException) {
            true
        } catch (e: Exception) {
            false
        }
    }

    fun parseLnurlpRequest(url: String) = LnurlpRequest.decodeFromUrl(url)

    /**
     * Verifies the signature on an UMA Lnurlp query based on the public key of the VASP making the request.
     */
    fun verifyUmaLnurlpQuerySignature(query: LnurlpRequest, pubKeyResponse: PubKeyResponse): Boolean {
        return verifySignature(query.signablePayload(), query.signature, pubKeyResponse.signingPubKey)
    }

    /**
     * Creates a signed UMA [LnurlpResponse].
     *
     * @param query The [LnurlpRequest] sent by the sender.
     * @param privateKeyBytes The signing private key of the VASP that is receiving the payment. This will be used to
     *     sign the response.
     * @param requiresTravelRuleInfo Indicates whether the receiving VASP is a financial institution which requires
     *     travel rule information.
     * @param callback The URL that the sending VASP will call to retrieve an invoice via a [PayRequest].
     * @param encodedMetadata The metadata that will be added to the invoice's metadata hash field.
     * @param minSendableSats The minimum amount of sats that the sender can send.
     * @param maxSendableSats The maximum amount of sats that the sender can send.
     * @param payerDataOptions The data that the sender must send to the receiver to identify themselves.
     * @param currencyOptions The list of currencies that the receiver accepts, along with their conversion rates.
     * @param receiverKycStatus Indicates whether VASP2 has KYC information about the receiver.
     * @return The [LnurlpResponse] that should be sent to the sender for the given [LnurlpRequest].
     * @throws IllegalArgumentException if the receiverAddress is not in the format of "user@domain".
     */
    fun getLnurlpResponse(
        query: LnurlpRequest,
        privateKeyBytes: ByteArray,
        requiresTravelRuleInfo: Boolean,
        callback: String,
        encodedMetadata: String,
        minSendableSats: Long,
        maxSendableSats: Long,
        payerDataOptions: PayerDataOptions,
        currencyOptions: List<Currency>,
        receiverKycStatus: KycStatus,
    ): LnurlpResponse {
        val complianceResponse =
            getSignedLnurlpComplianceResponse(query, privateKeyBytes, requiresTravelRuleInfo, receiverKycStatus)
        val umaVersion = minOf(Version.parse(query.umaVersion), Version.parse(UMA_VERSION_STRING)).toString()
        return LnurlpResponse(
            callback = callback,
            minSendable = minSendableSats,
            maxSendable = maxSendableSats,
            metadata = encodedMetadata,
            currencies = currencyOptions,
            requiredPayerData = payerDataOptions,
            compliance = complianceResponse,
            umaVersion = umaVersion,
        )
    }

    private fun getSignedLnurlpComplianceResponse(
        query: LnurlpRequest,
        privateKeyBytes: ByteArray,
        requiresTravelRuleInfo: Boolean,
        receiverKycStatus: KycStatus,
    ): LnurlComplianceResponse {
        val nonce = generateNonce()
        val timestamp = System.currentTimeMillis() / 1000
        val complianceResponse = LnurlComplianceResponse(
            kycStatus = receiverKycStatus,
            isSubjectToTravelRule = requiresTravelRuleInfo,
            signature = "",
            signatureNonce = nonce,
            signatureTimestamp = timestamp,
            receiverIdentifier = query.receiverAddress,
        )
        val signature = signPayload(complianceResponse.signablePayload(), privateKeyBytes)
        return complianceResponse.signedWith(signature)
    }

    fun parseAsLnurlpResponse(response: String): LnurlpResponse {
        return serializerFormat.decodeFromString(response)
    }

    /**
     * Verifies the signature on an UMA Lnurlp response based on the public key of the VASP making the request.
     *
     * @param response The signed [LnurlpResponse] sent by the receiver.
     * @param pubKeyResponse The [PubKeyResponse] that contains the public key of the receiver.
     */
    fun verifyLnurlpResponseSignature(response: LnurlpResponse, pubKeyResponse: PubKeyResponse): Boolean {
        val signablePayload = response.compliance.signablePayload()
        val hashedPayload = MessageDigest.getInstance("SHA-256").digest(signablePayload)
        return verifySignature(hashedPayload, response.compliance.signature, pubKeyResponse.signingPubKey)
    }

    /**
     * Creates a signed UMA [PayRequest].
     *
     * @param receiverEncryptionPubKey The public key of the receiver that will be used to encrypt the travel rule
     *     information.
     * @param sendingVaspPrivateKey The private key of the VASP that is sending the payment. This will be used to sign
     *     the request.
     * @param currencyCode The code of the currency that the receiver will receive for this payment.
     * @param amount The amount of the payment in the smallest unit of the specified currency (i.e. cents for USD).
     * @param payerIdentifier The identifier of the sender. For example, $alice@vasp1.com
     * @param payerKycStatus Indicates whether VASP1 has KYC information about the sender.
     * @param utxoCallback The URL that the receiver will call to send UTXOs of the channel that the receiver used to
     *   receive the payment once it completes.
     * @param travelRuleInfo The travel rule information. This will be encrypted before sending to the receiver.
     * @param payerUtxos The list of UTXOs of the sender's channels that might be used to fund the payment.
     * @param payerNodePubKey If known, the public key of the sender's node. If supported by the receiving VASP's
     *     compliance provider, this will be used to pre-screen the sender's UTXOs for compliance purposes.
     * @param payerName The name of the sender (optional).
     * @param payerEmail The email of the sender (optional).
     * @return The [PayRequest] that should be sent to the receiver.
     */
    @JvmOverloads
    fun getPayRequest(
        receiverEncryptionPubKey: ByteArray,
        sendingVaspPrivateKey: ByteArray,
        currencyCode: String,
        amount: Long,
        payerIdentifier: String,
        payerKycStatus: KycStatus,
        utxoCallback: String,
        travelRuleInfo: String? = null,
        payerUtxos: List<String>? = null,
        payerNodePubKey: String? = null,
        payerName: String? = null,
        payerEmail: String? = null,
    ): PayRequest {
        val compliancePayerData = getSignedCompliancePayerData(
            receiverEncryptionPubKey,
            sendingVaspPrivateKey,
            payerIdentifier,
            travelRuleInfo,
            payerKycStatus,
            payerUtxos,
            payerNodePubKey,
            utxoCallback,
        )
        val payerData = PayerData(
            identifier = payerIdentifier,
            name = payerName,
            email = payerEmail,
            compliance = compliancePayerData,
        )
        return PayRequest(
            payerData = payerData,
            currencyCode = currencyCode,
            amount = amount,
        )
    }

    private fun getSignedCompliancePayerData(
        receiverEncryptionPubKey: ByteArray,
        sendingVaspPrivateKey: ByteArray,
        payerIdentifier: String,
        travelRuleInfo: String?,
        payerKycStatus: KycStatus,
        payerUtxos: List<String>?,
        payerNodePubKey: String?,
        utxoCallback: String,
    ): CompliancePayerData {
        val nonce = generateNonce()
        val timestamp = System.currentTimeMillis() / 1000
        val unsignedCompliancePayerData = CompliancePayerData(
            utxos = payerUtxos ?: emptyList(),
            nodePubKey = payerNodePubKey,
            travelRuleInfo = travelRuleInfo?.let { encryptTravelRuleInfo(receiverEncryptionPubKey, it) },
            senderKycStatus = payerKycStatus,
            signature = "",
            signatureNonce = nonce,
            signatureTimestamp = timestamp,
            utxoCallback = utxoCallback,
        )
        val signablePayload = "$payerIdentifier|$nonce|$timestamp".encodeToByteArray()
        val signature = signPayload(signablePayload, sendingVaspPrivateKey)
        return unsignedCompliancePayerData.signedWith(signature)
    }


    private fun encryptTravelRuleInfo(receiverEncryptionPubKey: ByteArray, travelRuleInfoJson: String): String {
        return Secp256k1.encryptEcies(travelRuleInfoJson.encodeToByteArray(), receiverEncryptionPubKey).toHexString()
    }

    /**
     * Parses a json requestBody into a [PayRequest].
     *
     * @param request The json requestBody sent by the sender.
     * @return The [PayRequest] sent by the sender.
     * @throws IllegalArgumentException if the requestBody is not a valid [PayRequest].
     * @throws SerializationException if the requestBody is not a valid json.
     */
    @Throws(IllegalArgumentException::class, SerializationException::class)
    fun parseAsPayRequest(request: String): PayRequest {
        return serializerFormat.decodeFromString(request)
    }

    /**
     * Verifies the signature of the [PayRequest] sent by the sender.
     *
     * @param payReq The [PayRequest] sent by the sender.
     * @param pubKeyResponse The [PubKeyResponse] that contains the public key of the sender.
     * @return true if the signature is valid, false otherwise.
     */
    fun verifyPayReqSignature(payReq: PayRequest, pubKeyResponse: PubKeyResponse): Boolean {
        return verifySignature(payReq.signablePayload(), payReq.payerData.compliance!!.signature, pubKeyResponse.signingPubKey)
    }

    /**
     * Creates an uma pay request response with an encoded invoice.
     *
     * In Kotlin, prefer using [getPayReqResponse] instead.
     *
     * @param query The [PayRequest] sent by the sender.
     * @param invoiceCreator The [UmaInvoiceCreator] that will be used to create the invoice.
     * @param metadata The metadata that will be added to the invoice's metadata hash field.
     * @param currencyCode The code of the currency that the receiver will receive for this payment.
     * @param conversionRate The conversion rate. It is the numer of milli-satoshis per the smallest unit of the
     *     specified currency (for example: cents in USD). This rate is committed to by the receiving VASP until the
     *     invoice expires.
     * @param receiverFeesMillisats The fees charged (in millisats) by the receiving VASP for this transaction. This
     *     is separate from the [conversionRate].
     * @param receiverChannelUtxos The list of UTXOs of the receiver's channels that might be used to fund the payment.
     * @param receiverNodePubKey If known, the public key of the receiver's node. If supported by the sending VASP's
     *     compliance provider, this will be used to pre-screen the receiver's UTXOs for compliance purposes.
     * @param utxoCallback The URL that the receiving VASP will call to send UTXOs of the channel that the receiver
     *     used to receive the payment once it completes.
     * @return A [CompletableFuture] [PayReqResponse] that should be returned to the sender for the given [PayRequest].
     */
    @Throws(CryptoException::class, IllegalArgumentException::class, CancellationException::class)
    fun getPayReqResponseFuture(
        query: PayRequest,
        invoiceCreator: UmaInvoiceCreator,
        metadata: String,
        currencyCode: String,
        conversionRate: Long,
        receiverFeesMillisats: Long,
        receiverChannelUtxos: List<String>,
        receiverNodePubKey: String?,
        utxoCallback: String,
    ): CompletableFuture<PayReqResponse> = coroutineScope.future {
        getPayReqResponse(
            query,
            invoiceCreator,
            metadata,
            currencyCode,
            conversionRate,
            receiverFeesMillisats,
            receiverChannelUtxos,
            receiverNodePubKey,
            utxoCallback,
        )
    }

    /**
     * Creates an uma pay request response with an encoded invoice.
     *
     * This method is synchronous and should only be used in cases where the caller is already on a background thread.
     * In Kotlin, prefer using [getPayReqResponse] instead.
     *
     * @param query The [PayRequest] sent by the sender.
     * @param invoiceCreator The [UmaInvoiceCreator] that will be used to create the invoice.
     * @param metadata The metadata that will be added to the invoice's metadata hash field.
     * @param currencyCode The code of the currency that the receiver will receive for this payment.
     * @param conversionRate The conversion rate. It is the numer of milli-satoshis per the smallest unit of the
     *     specified currency (for example: cents in USD). This rate is committed to by the receiving VASP until the
     *     invoice expires.
     * @param receiverFeesMillisats The fees charged (in millisats) by the receiving VASP for this transaction. This
     *     is separate from the [conversionRate].
     * @param receiverChannelUtxos The list of UTXOs of the receiver's channels that might be used to fund the payment.
     * @param receiverNodePubKey If known, the public key of the receiver's node. If supported by the sending VASP's
     *     compliance provider, this will be used to pre-screen the receiver's UTXOs for compliance purposes.
     * @param utxoCallback The URL that the receiving VASP will call to send UTXOs of the channel that the receiver
     *     used to receive the payment once it completes.
     * @return A [PayReqResponse] that should be returned to the sender for the given [PayRequest].
     */
    @Throws(CryptoException::class, IllegalArgumentException::class, CancellationException::class)
    fun getPayReqResponseSync(
        query: PayRequest,
        invoiceCreator: UmaInvoiceCreator,
        metadata: String,
        currencyCode: String,
        conversionRate: Long,
        receiverFeesMillisats: Long,
        receiverChannelUtxos: List<String>,
        receiverNodePubKey: String?,
        utxoCallback: String,
    ): PayReqResponse = runBlocking {
        getPayReqResponse(
            query,
            invoiceCreator,
            metadata,
            currencyCode,
            conversionRate,
            receiverFeesMillisats,
            receiverChannelUtxos,
            receiverNodePubKey,
            utxoCallback,
        )
    }

    /**
     * Creates an uma pay request response with an encoded invoice.
     *
     * @param query The [PayRequest] sent by the sender.
     * @param invoiceCreator The [UmaInvoiceCreator] that will be used to create the invoice.
     * @param metadata The metadata that will be added to the invoice's metadata hash field.
     * @param currencyCode The code of the currency that the receiver will receive for this payment.
     * @param conversionRate The conversion rate. It is the numer of milli-satoshis per the smallest unit of the
     *     specified currency (for example: cents in USD). This rate is committed to by the receiving VASP until the
     *     invoice expires.
     * @param receiverFeesMillisats The fees charged (in millisats) by the receiving VASP for this transaction. This
     *     is separate from the [conversionRate].
     * @param receiverChannelUtxos The list of UTXOs of the receiver's channels that might be used to fund the payment.
     * @param receiverNodePubKey If known, the public key of the receiver's node. If supported by the sending VASP's
     *     compliance provider, this will be used to pre-screen the receiver's UTXOs for compliance purposes.
     * @param utxoCallback The URL that the receiving VASP will call to send UTXOs of the channel that the receiver
     *     used to receive the payment once it completes.
     * @return The [PayReqResponse] that should be returned to the sender for the given [PayRequest].
     */
    @JvmName("KotlinOnly-getPayReqResponseSuspended")
    suspend fun getPayReqResponse(
        query: PayRequest,
        invoiceCreator: UmaInvoiceCreator,
        metadata: String,
        currencyCode: String,
        conversionRate: Long,
        receiverFeesMillisats: Long,
        receiverChannelUtxos: List<String>,
        receiverNodePubKey: String?,
        utxoCallback: String,
    ): PayReqResponse {
        val encodedPayerData = serializerFormat.encodeToString(query.payerData)
        val metadataWithPayerData = "$metadata$encodedPayerData"
        val invoice = invoiceCreator.createUmaInvoice(
            amountMsats = query.amount * conversionRate,
            metadata = metadataWithPayerData,
        ).await()
        return PayReqResponse(
            encodedInvoice = invoice.data.encodedPaymentRequest,
            compliance = PayReqResponseCompliance(
                utxos = receiverChannelUtxos,
                nodePubKey = receiverNodePubKey,
                utxoCallback = utxoCallback,
            ),
            paymentInfo = PayReqResponsePaymentInfo(
                currencyCode = currencyCode,
                multiplier = conversionRate,
                exchangeFeesMillisatoshi = receiverFeesMillisats,
            ),
        )
    }

    fun parseAsPayReqResponse(response: String): PayReqResponse {
        return serializerFormat.decodeFromString(response)
    }

    @Throws(CryptoException::class)
    private fun signPayload(payload: ByteArray, privateKey: ByteArray): String {
        return Secp256k1.signEcdsa(payload, privateKey).toHexString()
    }

    @Throws(CryptoException::class)
    private fun verifySignature(payload: ByteArray, signature: String, publicKey: ByteArray): Boolean {
        return Secp256k1.verifyEcdsa(payload, signature.hexToByteArray(), publicKey)
    }

    fun getVaspDomainFromUmaAddress(identifier: String): String {
        val atIndex = identifier.indexOf('@')
        if (atIndex == -1) {
            throw IllegalArgumentException("Invalid identifier: $identifier. Must be of format \$user@domain.com")
        }
        return identifier.substring(atIndex + 1)
    }
}

interface UmaInvoiceCreator {
    /**
     * Creates an invoice with the given amount and encoded LNURL metadata.
     *
     * @param amountMsats The amount of the invoice in millisatoshis.
     * @param metadata The metadata that will be added to the invoice's metadata hash field.
     * @return The [Invoice] that should be returned to the sender for the given [PayRequest] wrapped in a
     *     [CompletableFuture].
     */
    fun createUmaInvoice(amountMsats: Long, metadata: String): CompletableFuture<Invoice>
}

class LightsparkClientUmaInvoiceCreator(
    private val client: LightsparkCoroutinesClient,
    private val nodeId: String,
    private val expirySecs: Int,
) : UmaInvoiceCreator {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    constructor(apiClientId: String, apiClientSecret: String, nodeId: String, expirySecs: Int) : this(
        LightsparkCoroutinesClient(
            ClientConfig(
                authProvider = AccountApiTokenAuthProvider(apiClientId, apiClientSecret),
            ),
        ),
        nodeId,
        expirySecs,
    )

    override fun createUmaInvoice(amountMsats: Long, metadata: String) = coroutineScope.future {
        client.createUmaInvoice(nodeId, amountMsats, metadata, expirySecs)
    }
}
