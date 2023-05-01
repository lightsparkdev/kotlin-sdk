package com.lightspark.androidwalletdemo.requestpayment

import com.lightspark.androidwalletdemo.util.CurrencyAmountArg

data class RequestPaymentUiState(
    val encodedQrData: String,
    val invoiceAmount: CurrencyAmountArg? = null,
)
