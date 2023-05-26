package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.CreateTestModeInvoiceOutput

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
        ...CreateTestModeInvoiceOutputFragment
    }
}

${CreateTestModeInvoiceOutput.FRAGMENT}
"""
