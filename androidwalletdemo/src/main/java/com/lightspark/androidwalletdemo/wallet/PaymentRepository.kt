package com.lightspark.androidwalletdemo.wallet

import com.lightspark.androidwalletdemo.util.CurrencyAmountArg
import com.lightspark.sdk.core.asLce
import com.lightspark.sdk.core.wrapWithLceFlow
import com.lightspark.sdk.wallet.LightsparkCoroutinesWalletClient
import com.lightspark.sdk.wallet.model.InvoiceType
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class PaymentRepository @Inject constructor(private val lightsparkClient: LightsparkCoroutinesWalletClient) {
    fun createInvoice(amount: CurrencyAmountArg, memo: String? = null) =
        wrapWithLceFlow {
            val amountMillis = amount.toMilliSats()
            val type = if (amountMillis == 0L) InvoiceType.AMP else InvoiceType.STANDARD
            lightsparkClient.createInvoice(amountMillis, memo, type)
        }.flowOn(Dispatchers.IO)

    suspend fun payInvoice(invoice: String) =
        lightsparkClient.payInvoiceAndAwaitCompletion(invoice, 1000000).asLce().flowOn(Dispatchers.IO)

    fun decodeInvoice(encodedInvoice: String) =
        wrapWithLceFlow { lightsparkClient.decodeInvoice(encodedInvoice) }.flowOn(Dispatchers.IO)
}
