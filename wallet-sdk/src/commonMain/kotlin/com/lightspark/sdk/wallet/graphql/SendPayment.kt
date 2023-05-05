package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.OutgoingPayment

const val SendPaymentMutation = """
  mutation SendPayment(
    ${'$'}destination_public_key: String!
    ${'$'}timeout_secs: Int!
    ${'$'}amount_msats: Long!
    ${'$'}maximum_fees_msats: Long!
  ) {
    send_payment(
      input: {
        destination_public_key: ${'$'}destination_public_key
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
