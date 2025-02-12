package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.OutgoingPayment

const val PayUmaInvoiceMutation = """
  mutation PayUmaInvoice(
    ${'$'}node_id: ID!
    ${'$'}encoded_invoice: String!
    ${'$'}timeout_secs: Int!
    ${'$'}maximum_fees_msats: Long!
    ${'$'}amount_msats: Long
    ${'$'}sender_hash: String
    ${'$'}idempotency_key: String
  ) {
    pay_uma_invoice(
      input: {
        node_id: ${'$'}node_id
        encoded_invoice: ${'$'}encoded_invoice
        timeout_secs: ${'$'}timeout_secs
        amount_msats: ${'$'}amount_msats
        maximum_fees_msats: ${'$'}maximum_fees_msats
        sender_hash: ${'$'}sender_hash
        idempotency_key: ${'$'}idempotency_key
      }
    ) {
      payment {
        ...OutgoingPaymentFragment
      }
    }
  }

  ${OutgoingPayment.FRAGMENT}
"""
