package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.Invoice

const val CreateInvoiceMutation = """
  mutation CreateInvoice(
    ${'$'}nodeId: ID!
    ${'$'}amountMsats: Long!
    ${'$'}memo: String
    ${'$'}type: InvoiceType = null
    ${'$'}expirySecs: Int = null
    ) {
    create_invoice(input: { node_id: ${'$'}nodeId, amount_msats: ${'$'}amountMsats, memo: ${'$'}memo, invoice_type: ${'$'}type, expiry_secs: ${'$'}expirySecs }) {
      invoice {
        ...InvoiceFragment
      }
    }
  }
  
    ${Invoice.FRAGMENT}
"""
