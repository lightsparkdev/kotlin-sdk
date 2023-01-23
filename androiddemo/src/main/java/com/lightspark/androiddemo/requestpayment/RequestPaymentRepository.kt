package com.lightspark.androiddemo.requestpayment

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.sdk.Lce
import com.lightspark.sdk.LightsparkWalletClient
import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.wrapWithLceFlow
import kotlinx.coroutines.flow.Flow

class RequestPaymentRepository(private val lightsparkClient: LightsparkWalletClient = LightsparkClientProvider.walletClient) {
    suspend fun createInvoice(amount: CurrencyAmount, memo: String? = null) =
        wrapWithLceFlow { lightsparkClient.createInvoice(amount, memo) }

    fun getWalletAddress(): Flow<Lce<String>> =
        wrapWithLceFlow {
            // TODO(Jeremy): Fetch wallet address for real.
            lightsparkClient.activeWalletId ?: "No wallet ID"
        }
}