package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.Invoice

const val CreateInvoiceMutation = """
  mutation CreateInvoice(
    ${'$'}amountMsats: Long!
    ${'$'}memo: String
    ${'$'}type: InvoiceType = null
    ${'$'}expirySecs: Int = null
    ) {
    ) {
    create_invoice(input: { amount_msats: ${'$'}amountMsats, memo: ${'$'}memo, invoice_type: ${'$'}type }, expiry_secs: ${'$'}expirySecs) {
      invoice {
        ...InvoiceFragment
      }
    }
  }
  
    ${Invoice.FRAGMENT}
"""
