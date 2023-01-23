package com.lightspark.androiddemo.requestpayment

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.sdk.Lce
import com.lightspark.sdk.LightsparkClient
import com.lightspark.sdk.model.CurrencyAmount
import kotlinx.coroutines.flow.Flow

class RequestPaymentRepository(private val lightsparkClient: LightsparkClient = LightsparkClientProvider.client) {
    suspend fun createInvoice(
        nodeId: String,
        amount: CurrencyAmount,
        memo: String? = null
    ) = lightsparkClient.wrapFlowableResult { lightsparkClient.createInvoice(nodeId, amount, memo) }

    fun getWalletAddress(nodeId: String): Flow<Lce<String>> =
        lightsparkClient.wrapFlowableResult {
            // TODO(Jeremy): Fetch wallet address for real.
            nodeId
        }
}