package com.lightspark.androidwalletdemo.sendpayment

import com.lightspark.androidwalletdemo.util.CurrencyAmountArg

enum class InputType { SCAN_QR, MANUAL_ENTRY }
enum class PaymentStatus { NOT_STARTED, PENDING, SUCCESS, FAILURE }

data class SendPaymentUiState(
    val inputType: InputType,
    val memo: String?,
    val amount: CurrencyAmountArg,
    val paymentStatus: PaymentStatus,
)
