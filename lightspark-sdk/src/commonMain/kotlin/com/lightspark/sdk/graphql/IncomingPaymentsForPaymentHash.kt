package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.IncomingPayment

const val IncomingPaymentsForPaymentHashQuery = """
query OutgoingPaymentsForInvoice(
    ${'$'}paymentHash: Hash32!,
    ${'$'}transactionStatuses: [TransactionStatus!] = null
) {
    incoming_payments_for_payment_hash(input: {
        payment_hash: ${'$'}paymentHash,
        statuses: ${'$'}transactionStatuses
    }) {
        payments {
            ...IncomingPaymentFragment
        }
    }
}

${IncomingPayment.FRAGMENT}
"""
