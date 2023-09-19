package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.SignInvoiceOutput

const val SignInvoiceMutation = """
mutation SignInvoice(
  ${'$'}invoice_id : ID!
  ${'$'}signature : Signature!
  ${'$'}recovery_id : Int!
) {
    sign_invoice(input: {
		invoice_id: ${'$'}invoice_id
		signature: ${'$'}signature
		recovery_id: ${'$'}recovery_id
	}) {
        ...SignInvoiceOutputFragment
    }
}

${SignInvoiceOutput.FRAGMENT}
"""
