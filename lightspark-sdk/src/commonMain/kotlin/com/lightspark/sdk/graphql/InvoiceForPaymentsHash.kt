package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.Invoice

const val InvoiceForPaymentsHashQuery = """
query InvoiceForPaymentHash(
    ${'$'}paymentHash: Hash32!,
) {
	invoice_for_payment_hash(input: {
		payment_hash: ${'$'}paymentHash,
	}) {
		invoice {
			...InvoiceFragment
		}
	}
}

  ${Invoice.FRAGMENT}
"""
