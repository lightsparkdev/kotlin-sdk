package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.LightningFeeEstimateOutput

const val LightningFeeEstimateForInvoiceQuery = """
  query LightningFeeEstimateForInvoice(
    ${'$'}encoded_payment_request: String!
    ${'$'}amount_msats: Long
  ) {
    lightning_fee_estimate_for_invoice(input: {
      encoded_payment_request: ${'$'}encoded_payment_request,
      amount_msats: ${'$'}amount_msats
    }) {
      ...LightningFeeEstimateOutputFragment
    }
  }

${LightningFeeEstimateOutput.FRAGMENT}
"""
