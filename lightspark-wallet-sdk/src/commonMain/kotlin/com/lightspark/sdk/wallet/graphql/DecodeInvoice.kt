package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.InvoiceData

const val DecodeInvoiceQuery = """
  query DecodeInvoice(${'$'}encoded_payment_request: String!) {
    decoded_payment_request(encoded_payment_request: ${'$'}encoded_payment_request) {
      __typename
      ... on InvoiceData {
        ...InvoiceDataFragment
      }
    }
  }

${InvoiceData.FRAGMENT}
"""
