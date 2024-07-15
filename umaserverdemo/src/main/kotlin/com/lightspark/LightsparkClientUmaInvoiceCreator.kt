package com.lightspark

import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future
import me.uma.UmaInvoiceCreator

class LightsparkClientUmaInvoiceCreator @JvmOverloads constructor(
    private val client: LightsparkCoroutinesClient,
    private val nodeId: String,
    private val expirySecs: Int,
    private val signingPrivateKey: ByteArray? = null,
    private val receiverIdentifier: String? = null,
) : UmaInvoiceCreator {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @JvmOverloads
    constructor(
        apiClientId: String,
        apiClientSecret: String,
        nodeId: String,
        expirySecs: Int,
        signingPrivateKey: ByteArray? = null,
        receiverIdentifier: String? = null
    ) : this(
        LightsparkCoroutinesClient(
            ClientConfig(
                authProvider = AccountApiTokenAuthProvider(apiClientId, apiClientSecret),
            ),
        ),
        nodeId,
        expirySecs,
        signingPrivateKey,
        receiverIdentifier,
    )

    override fun createUmaInvoice(amountMsats: Long, metadata: String) = coroutineScope.future {
        client.createUmaInvoice(nodeId, amountMsats, metadata, expirySecs, signingPrivateKey, receiverIdentifier).data.encodedPaymentRequest
    }
}
