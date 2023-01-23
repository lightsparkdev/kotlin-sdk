package com.lightspark.sdk.model

import com.lightspark.api.fragment.TransactionDetails

internal fun TransactionDetails.toTransaction() =
    Transaction(
        id = id,
        amount = CurrencyAmount(amount.value, amount.unit),
        createdAt = created_at as String,
        resolvedAt = resolved_at as String,
        memo = onIncomingPayment?.payment_request?.data?.onInvoiceData?.memo,
        status = status,
        type = transactionType(),
        otherAddress = otherAddress(),
        transactionHash = transaction_hash
    )

private fun TransactionDetails.transactionType(): Transaction.Type {
    return when {
        onIncomingPayment != null -> Transaction.Type.PAYMENT_RECEIVED
        onOutgoingPayment != null -> Transaction.Type.PAYMENT_SENT
        onDeposit != null -> Transaction.Type.DEPOSIT
        onWithdrawal != null -> Transaction.Type.WITHDRAWAL
        else -> Transaction.Type.UNKNOWN
    }
}

private fun TransactionDetails.otherAddress(): String? {
    return when {
        onIncomingPayment != null -> onIncomingPayment.incoming_payment_origin?.public_key
        onOutgoingPayment != null -> onOutgoingPayment.outgoing_payment_destination?.public_key
        onDeposit != null -> onDeposit.deposit_destination.public_key
        onWithdrawal != null -> onWithdrawal.withdraw_origin.public_key
        else -> null
    }
}