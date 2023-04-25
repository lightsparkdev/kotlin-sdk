package com.lightspark.sdk.server.graphql

import com.lightspark.sdk.server.model.LightningFeeEstimateOutput

const val LightningFeeEstimateForInvoiceQuery = """
  query LightningFeeEstimateForInvoice(
    ${'$'}node_id: ID!
    ${'$'}encoded_payment_request: String!
    ${'$'}amount_msats: Long
  ) {
    lightning_fee_estimate_for_invoice(input: {
      node_id: ${'$'}node_id,
      encoded_payment_request: ${'$'}encoded_payment_request,
      amount_msats: ${'$'}amount_msats
    }) {
      ...LightningFeeEstimateOutputFragment
    }
  }

${LightningFeeEstimateOutput.FRAGMENT}
"""
