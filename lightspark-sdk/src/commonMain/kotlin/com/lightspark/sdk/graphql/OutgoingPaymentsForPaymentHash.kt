package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.OutgoingPayment

const val OutgoingPaymentsForPaymentHashQuery = """
query OutgoingPaymentsForPaymentHash(
	${'$'}paymentHash: Hash32!,
	${'$'}transactionStatuses: [TransactionStatus!] = null
) {
	outgoing_payments_for_payment_hash(input: {
		payment_hash: ${'$'}paymentHash,
		statuses: ${'$'}transactionStatuses
	}) {
		payments {
			...OutgoingPaymentFragment
		}
	}
}

  ${OutgoingPayment.FRAGMENT}
"""