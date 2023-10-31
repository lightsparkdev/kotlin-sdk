package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.OutgoingPayment

const val OutgoingPaymentsForInvoiceQuery = """
query OutgoingPaymentsForInvoice(
    ${'$'}encodedInvoice: ID!,
    ${'$'}transactionStatuses: [TransactionStatus!] = null
) {
    outgoing_payments_for_invoice(input: {
        encoded_invoice: ${'$'}encodedInvoice,
        statuses: ${'$'}transactionStatuses
    }) {
        payments {
            ...OutgoingPaymentFragment
        }
    }
}

${OutgoingPayment.FRAGMENT}
"""
