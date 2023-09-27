package com.lightspark

import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import me.uma.UmaInvoiceCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future

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
        client.createUmaInvoice(nodeId, amountMsats, metadata, expirySecs).data.encodedPaymentRequest
    }
}
