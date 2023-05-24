package com.lightspark.sdk.wallet.graphql

const val CreateTestModeInvoice = """
mutation CreateTestModeInvoice(
    ${'$'}amount_msats: Long!
    ${'$'}memo: String
    ${'$'}invoice_type: InvoiceType
) {
    create_test_mode_invoice(input: {
        amount_msats: ${'$'}amount_msats
        memo: ${'$'}memo
        invoice_type: ${'$'}invoice_type
    }) {
        encoded_payment_request
    }
}
"""
