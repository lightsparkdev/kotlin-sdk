package com.lightspark.androidwalletdemo.wallet

import com.lightspark.androidwalletdemo.util.CurrencyAmountArg
import com.lightspark.sdk.core.wrapWithLceFlow
import com.lightspark.sdk.wallet.LightsparkCoroutinesWalletClient
import com.lightspark.sdk.wallet.model.InvoiceType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PaymentRepository @Inject constructor(private val lightsparkClient: LightsparkCoroutinesWalletClient) {
    fun createInvoice(amount: CurrencyAmountArg, memo: String? = null) =
        wrapWithLceFlow {
            val amountMillis = amount.toMilliSats()
            val type = if (amountMillis == 0L) InvoiceType.AMP else InvoiceType.STANDARD
            lightsparkClient.createInvoice(amountMillis, memo, type)
        }.flowOn(Dispatchers.IO)

    fun payInvoice(invoice: String) =
        wrapWithLceFlow { lightsparkClient.payInvoice(invoice, 1000000) }.flowOn(Dispatchers.IO)

    fun decodeInvoice(encodedInvoice: String) =
        wrapWithLceFlow { lightsparkClient.decodeInvoice(encodedInvoice) }.flowOn(Dispatchers.IO)
}
