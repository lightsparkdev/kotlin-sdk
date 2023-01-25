package com.lightspark.androiddemo.wallet

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.sdk.Lce
import com.lightspark.sdk.LightsparkWalletClient
import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.wrapWithLceFlow
import kotlinx.coroutines.flow.Flow

class PaymentRepository(private val lightsparkClient: LightsparkWalletClient = LightsparkClientProvider.walletClient) {
    fun createInvoice(amount: CurrencyAmount, memo: String? = null) =
        wrapWithLceFlow { lightsparkClient.createInvoice(amount, memo) }

    fun payInvoice(invoice: String) =
        wrapWithLceFlow { lightsparkClient.payInvoice(invoice) }

    fun decodeInvoice(encodedInvoice: String) =
        wrapWithLceFlow { lightsparkClient.decodeInvoice(encodedInvoice) }

    fun getWalletAddress(): Flow<Lce<String>> =
        wrapWithLceFlow {
            // TODO(Jeremy): Fetch wallet address for real.
            lightsparkClient.activeWalletId ?: "No wallet ID"
        }
}