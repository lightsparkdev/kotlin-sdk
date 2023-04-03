package com.lightspark.androiddemo.wallet

import com.lightspark.androiddemo.util.CurrencyAmountArg
import com.lightspark.sdk.Lce
import com.lightspark.sdk.LightsparkWalletClient
import com.lightspark.sdk.wrapWithLceFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PaymentRepository @Inject constructor(private val lightsparkClient: LightsparkWalletClient) {
    fun createInvoice(amount: CurrencyAmountArg, memo: String? = null) =
        wrapWithLceFlow { lightsparkClient.createInvoice(amount.toMilliSats(), memo) }.flowOn(Dispatchers.IO)

    fun payInvoice(invoice: String) =
        wrapWithLceFlow { lightsparkClient.payInvoice(invoice, 1000000) }.flowOn(Dispatchers.IO)

    fun decodeInvoice(encodedInvoice: String) =
        wrapWithLceFlow { lightsparkClient.decodeInvoice(encodedInvoice) }.flowOn(Dispatchers.IO)

    fun getWalletAddress(): Flow<Lce<String>> =
        wrapWithLceFlow {
            // TODO: Fetch wallet address for real.
            lightsparkClient.activeWalletId ?: "No wallet ID"
        }
}
