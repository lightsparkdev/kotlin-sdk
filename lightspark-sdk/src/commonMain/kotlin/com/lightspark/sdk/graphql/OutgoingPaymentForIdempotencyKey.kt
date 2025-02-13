package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.OutgoingPayment

const val OutgoingPaymentForIdempotencyKeyQuery = """
query OutgoingPaymentForIdempotencyKey(
	${'$'}idempotency_key: String!
) {
	outgoing_payment_for_idempotency_key(input: {
		idempotency_key: ${'$'}idempotency_key
	}) {
		payment {
			...OutgoingPaymentFragment
		}
	}
}

  ${OutgoingPayment.FRAGMENT}
"""
