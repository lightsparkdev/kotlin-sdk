package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.InvoiceData

const val CreateInvoiceMutation = """
  mutation CreateInvoice(
    ${'$'}amountMsats: Long!
    ${'$'}memo: String
    ${'$'}type: InvoiceType = null
    ) {
    create_invoice(input: { amount_msats: ${'$'}amountMsats, memo: ${'$'}memo, invoice_type: ${'$'}type }) {
      invoice {
        data {
          ...InvoiceDataFragment
        }
      }
    }
  }
  
    ${InvoiceData.FRAGMENT}
"""
