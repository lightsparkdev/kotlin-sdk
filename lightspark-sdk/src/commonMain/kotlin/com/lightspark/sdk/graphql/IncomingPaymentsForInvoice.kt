package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.IncomingPayment

const val IncomingPaymentsForInvoiceQuery = """
query IncomingPaymentsForInvoice(
    ${'$'}invoiceId: Hash32!,
    ${'$'}transactionStatuses: [TransactionStatus!] = null
) {
    incoming_payments_for_invoice_query(input: {
        invoice_id: ${'$'}invoiceId,
        statuses: ${'$'}transactionStatuses
    }) {
        payments {
            ...IncomingPaymentFragment
        }
    }
}

${IncomingPayment.FRAGMENT}
"""
