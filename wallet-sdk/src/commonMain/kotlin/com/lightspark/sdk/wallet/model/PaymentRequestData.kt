// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName

/**
 * The interface of a payment request on the Lightning Network (a.k.a. Lightning Invoice).
 */
interface PaymentRequestData {

    @SerialName("payment_request_data_encoded_payment_request")
    val encodedPaymentRequest: String

    @SerialName("payment_request_data_bitcoin_network")
    val bitcoinNetwork: BitcoinNetwork

    companion object {

        const val FRAGMENT = """
fragment PaymentRequestDataFragment on PaymentRequestData {
    type: __typename
    ... on InvoiceData {
        type: __typename
        invoice_data_encoded_payment_request: encoded_payment_request
        invoice_data_bitcoin_network: bitcoin_network
        invoice_data_payment_hash: payment_hash
        invoice_data_amount: amount {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        invoice_data_created_at: created_at
        invoice_data_expires_at: expires_at
        invoice_data_memo: memo
    }
}"""
    }
}
