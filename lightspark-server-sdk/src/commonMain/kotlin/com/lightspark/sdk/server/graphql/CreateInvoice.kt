package com.lightspark.sdk.server.graphql

import com.lightspark.sdk.server.model.InvoiceData

const val CreateInvoiceMutation = """
  mutation CreateInvoice(
    ${'$'}nodeId: ID!
    ${'$'}amountMsats: Long!
    ${'$'}memo: String
    ${'$'}type: InvoiceType = null
    ) {
    create_invoice(input: { node_id: ${'$'}nodeId, amount_msats: ${'$'}amountMsats, memo: ${'$'}memo, invoice_type: ${'$'}type }) {
      invoice {
        data {
          ...InvoiceDataFragment
        }
      }
    }
  }
  
    ${InvoiceData.FRAGMENT}
"""
