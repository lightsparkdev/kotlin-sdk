package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.SetInvoicePaymentHashOutput

const val SetInvoicePaymentHashMutation = """
mutation SetInvoicePaymentHash(
  ${'$'}invoice_id: ID!
  ${'$'}payment_hash: Hash32!
  ${'$'}preimage_nonce: Hash32!
) {
    set_invoice_payment_hash(input: {
		invoice_id: ${'$'}invoice_id
		payment_hash: ${'$'}payment_hash
		preimage_nonce: ${'$'}preimage_nonce
	}) {
        ...SetInvoicePaymentHashOutputFragment
    }
}

${SetInvoicePaymentHashOutput.FRAGMENT}
"""
