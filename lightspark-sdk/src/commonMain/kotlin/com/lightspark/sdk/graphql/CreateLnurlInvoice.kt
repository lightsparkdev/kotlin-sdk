package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.Invoice

const val CreateLnurlInvoiceMutation = """
  mutation CreateLnurlInvoice(
    ${'$'}nodeId: ID!
    ${'$'}amountMsats: Long!
    ${'$'}metadataHash: String!
    ${'$'}expirySecs: Int = null
    ${'$'}receiverHash: String = null
  ) {
    create_lnurl_invoice(
      input: {
        node_id: ${'$'}nodeId
        amount_msats: ${'$'}amountMsats
        metadata_hash: ${'$'}metadataHash
        expiry_secs: ${'$'}expirySecs
        receiver_hash: ${'$'}receiverHash
      }
    ) {
      invoice {
        ...InvoiceFragment
      }
    }
  }
  
    ${Invoice.FRAGMENT}
"""
