package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.OutgoingPayment

const val PayInvoiceMutation = """
  mutation PayInvoice(
    ${'$'}node_id: ID!
    ${'$'}encoded_invoice: String!
    ${'$'}timeout_secs: Int!
    ${'$'}maximum_fees_msats: Long!
    ${'$'}amount_msats: Long
  ) {
    pay_invoice(
      input: {
        node_id: ${'$'}node_id
        encoded_invoice: ${'$'}encoded_invoice
        timeout_secs: ${'$'}timeout_secs
        amount_msats: ${'$'}amount_msats
        maximum_fees_msats: ${'$'}maximum_fees_msats
      }
    ) {
      payment {
        ...OutgoingPaymentFragment
      }
    }
  }

  ${OutgoingPayment.FRAGMENT}
"""
