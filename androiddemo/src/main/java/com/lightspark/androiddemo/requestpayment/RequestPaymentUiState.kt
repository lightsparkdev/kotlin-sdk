package com.lightspark.androiddemo.requestpayment

import com.lightspark.androiddemo.util.CurrencyAmountArg

data class RequestPaymentUiState(
    val encodedQrData: String,
    val walletAddress: String,
    val invoiceAmount: CurrencyAmountArg? = null,
)
