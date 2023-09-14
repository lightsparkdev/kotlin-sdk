// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This object represents post-transaction data that could be used to register payment for KYT.
 * @param utxo The utxo of the channel over which the payment went through in the format of <transaction_hash>:<output_index>.
 * @param amount The amount of funds transferred in the payment.
 */
@Serializable
@SerialName("PostTransactionData")
data class PostTransactionData(
    @SerialName("post_transaction_data_utxo")
    val utxo: String,
    @SerialName("post_transaction_data_amount")
    val amount: CurrencyAmount,
) {
    companion object {
        const val FRAGMENT = """
fragment PostTransactionDataFragment on PostTransactionData {
    type: __typename
    post_transaction_data_utxo: utxo
    post_transaction_data_amount: amount {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
}"""
    }
}
