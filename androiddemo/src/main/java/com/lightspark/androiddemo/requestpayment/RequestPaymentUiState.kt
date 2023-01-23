package com.lightspark.androiddemo.requestpayment

import com.lightspark.sdk.model.CurrencyAmount

data class RequestPaymentsUiState(
    val encodedQrData: String,
    val walletAddress: String,
    val invoiceAmount: CurrencyAmount? = null
)