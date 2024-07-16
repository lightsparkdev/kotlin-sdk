package com.lightspark

import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future
import me.uma.UmaInvoiceCreator

/**
 * Creates UMA invoices using the Lightspark client.
 *
 * @constructor Creates an instance of [LightsparkClientUmaInvoiceCreator].
 *
 * @param client The [LightsparkCoroutinesClient] used to create invoices.
 * @param nodeId The ID of the node for which to create the invoice.
 * @param expirySecs The number of seconds before the invoice expires.
 * @param enableUmaAnalytics A flag indicating whether UMA analytics should be enabled. If `true`,
 *      the receiver identifier will be hashed using a monthly-rotated seed and used for anonymized
 *      analysis.
 * @param signingPrivateKey Optional, the receiver's signing private key. Used to hash the receiver
 *      identifier if UMA analytics is enabled.
 */
class LightsparkClientUmaInvoiceCreator @JvmOverloads constructor(
    private val client: LightsparkCoroutinesClient,
    private val nodeId: String,
    private val expirySecs: Int,
    private val enableUmaAnalytics: Boolean = false,
    private val signingPrivateKey: ByteArray? = null,
) : UmaInvoiceCreator {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @JvmOverloads
    constructor(
        apiClientId: String,
        apiClientSecret: String,
        nodeId: String,
        expirySecs: Int,
        enableUmaAnalytics: Boolean = false,
        signingPrivateKey: ByteArray? = null,
    ) : this(
        LightsparkCoroutinesClient(
            ClientConfig(
                authProvider = AccountApiTokenAuthProvider(apiClientId, apiClientSecret),
            ),
        ),
        nodeId,
        expirySecs,
        enableUmaAnalytics,
        signingPrivateKey,
    )

    override fun createUmaInvoice(amountMsats: Long, metadata: String, receiverIdentifier: String?) = coroutineScope.future {
        if (enableUmaAnalytics && signingPrivateKey != null) {
            client.createUmaInvoice(nodeId, amountMsats, metadata, expirySecs, signingPrivateKey, receiverIdentifier)
        } else {
            client.createUmaInvoice(nodeId, amountMsats, metadata, expirySecs)
        }.data.encodedPaymentRequest
    }
}
