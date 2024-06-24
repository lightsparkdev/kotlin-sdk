package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.Invoice

const val CreateUmaInvoiceMutation = """
  mutation CreateUmaInvoice(
    ${'$'}nodeId: ID!
    ${'$'}amountMsats: Long!
    ${'$'}metadataHash: String!
    ${'$'}expirySecs: Int = null
    ${'$'}receiverHash: String
  ) {
    create_uma_invoice(
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
