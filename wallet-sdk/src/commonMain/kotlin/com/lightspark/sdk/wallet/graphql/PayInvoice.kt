package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.OutgoingPayment

const val PayInvoiceMutation = """
  mutation PayInvoice(
    ${'$'}encoded_invoice: String!
    ${'$'}timeout_secs: Int!
    ${'$'}maximum_fees_msats: Long!
    ${'$'}amount_msats: Long
  ) {
    pay_invoice(
      input: {
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
