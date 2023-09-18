package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.ReleasePaymentPreimageOutput

const val ReleasePaymentPreimageMutation = """
mutation ReleasePaymentPreimage(
  ${'$'}invoice_id: ID!
  ${'$'}payment_preimage: Hash32!
) {
    release_payment_preimage(input: {
		invoice_id: ${'$'}invoice_id
		payment_preimage: ${'$'}payment_preimage
	}) {
        ...ReleasePaymentPreimageOutputFragment
    }
}

${ReleasePaymentPreimageOutput.FRAGMENT}
"""
