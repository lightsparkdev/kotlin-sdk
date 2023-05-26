package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.CreateTestModeInvoiceOutput

const val CreateTestModeInvoice = """
mutation CreateTestModeInvoice(
    ${'$'}local_node_id: ID!
    ${'$'}amount_msats: Long!
    ${'$'}memo: String
    ${'$'}invoice_type: InvoiceType
) {
    create_test_mode_invoice(input: {
        local_node_id: ${'$'}local_node_id
        amount_msats: ${'$'}amount_msats
        memo: ${'$'}memo
        invoice_type: ${'$'}invoice_type
    }) {
        ...CreateTestModeInvoiceOutputFragment
    }
}

${CreateTestModeInvoiceOutput.FRAGMENT}
"""
