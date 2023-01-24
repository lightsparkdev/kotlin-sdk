package com.lightspark.androiddemo.sendpayment

import com.lightspark.sdk.model.CurrencyAmount

enum class InputType { SCAN_QR, MANUAL_ENTRY }
enum class PaymentStatus { NOT_STARTED, PENDING, SUCCESS, FAILURE }

data class SendPaymentUiState(
    val inputType: InputType,
    val destinationAddress: String?,
    val amount: CurrencyAmount,
    val paymentStatus: PaymentStatus
)
