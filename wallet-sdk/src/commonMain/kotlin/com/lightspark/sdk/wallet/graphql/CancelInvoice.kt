package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.Invoice

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
