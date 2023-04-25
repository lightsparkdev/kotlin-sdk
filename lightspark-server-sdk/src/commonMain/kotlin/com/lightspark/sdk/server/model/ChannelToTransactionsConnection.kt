// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @param averageFee The average fee for the transactions that transited through this channel, according to the filters and constraints of the connection.
 * @param totalAmountTransacted The total amount transacted for the transactions that transited through this channel, according to the filters and constraints of the connection.
 * @param totalFees The total amount of fees for the transactions that transited through this channel, according to the filters and constraints of the connection.
 */
@Serializable
@SerialName("ChannelToTransactionsConnection")
data class ChannelToTransactionsConnection(

    @SerialName("channel_to_transactions_connection_count")
    val count: Int,
    @SerialName("channel_to_transactions_connection_average_fee")
    val averageFee: CurrencyAmount? = null,
    @SerialName("channel_to_transactions_connection_total_amount_transacted")
    val totalAmountTransacted: CurrencyAmount? = null,
    @SerialName("channel_to_transactions_connection_total_fees")
    val totalFees: CurrencyAmount? = null,
) {

    companion object {

        const val FRAGMENT = """
fragment ChannelToTransactionsConnectionFragment on ChannelToTransactionsConnection {
    type: __typename
    channel_to_transactions_connection_count: count
    channel_to_transactions_connection_average_fee: average_fee {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_to_transactions_connection_total_amount_transacted: total_amount_transacted {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_to_transactions_connection_total_fees: total_fees {
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
