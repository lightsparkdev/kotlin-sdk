package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.InvoiceData

const val CreateLnurlInvoiceMutation = """
  mutation CreateLnurlInvoice(
    ${'$'}nodeId: ID!
    ${'$'}amountMsats: Long!
    ${'$'}metadataHash: String!
    ) {
    create_lnurl_invoice(input: { node_id: ${'$'}nodeId, amount_msats: ${'$'}amountMsats, metadata_hash: ${'$'}metadataHash }) {
      invoice {
        data {
          ...InvoiceDataFragment
        }
      }
    }
  }
  
    ${InvoiceData.FRAGMENT}
"""
