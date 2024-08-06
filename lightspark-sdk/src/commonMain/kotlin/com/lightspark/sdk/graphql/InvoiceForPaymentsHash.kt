package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.IncomingPayment

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

  ${IncomingPayment.FRAGMENT}
"""