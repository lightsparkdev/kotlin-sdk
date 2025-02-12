package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.OutgoingPayment

const val SendPaymentMutation = """
  mutation SendPayment(
    ${'$'}node_id: ID!
    ${'$'}destination_public_key: String!
    ${'$'}timeout_secs: Int!
    ${'$'}amount_msats: Long!
    ${'$'}maximum_fees_msats: Long!
    ${'$'}idempotency_key: String
  ) {
    send_payment(
      input: {
        node_id: ${'$'}node_id
        destination_public_key: ${'$'}destination_public_key
        timeout_secs: ${'$'}timeout_secs
        amount_msats: ${'$'}amount_msats
        maximum_fees_msats: ${'$'}maximum_fees_msats
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
