package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.Invoice

const val CancelInvoiceMutation = """
  mutation CancelInvoice(
    ${'$'}invoiceId: ID!
  ) {
    cancel_invoice(input: { invoice_id: ${'$'}invoiceId }) {
      invoice {
        ...InvoiceFragment
      }
    }
  }
  
    ${Invoice.FRAGMENT}
"""
