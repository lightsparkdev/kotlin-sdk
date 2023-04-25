package com.lightspark.sdk.server.graphql

import com.lightspark.sdk.server.model.InvoiceData

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
