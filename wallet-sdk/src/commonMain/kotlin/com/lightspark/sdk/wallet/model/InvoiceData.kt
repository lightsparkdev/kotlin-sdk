// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This object represents the data associated with a BOLT #11 invoice. You can retrieve this object to receive the relevant data associated with a specific invoice.
 * @param paymentHash The payment hash of this invoice.
 * @param amount The requested amount in this invoice. If it is equal to 0, the sender should choose the amount to send.
 * @param createdAt The date and time when this invoice was created.
 * @param expiresAt The date and time when this invoice will expire.
 * @param destination The lightning node that will be paid when fulfilling this invoice.
 * @param memo A short, UTF-8 encoded, description of the purpose of this invoice.
 */
@Serializable
@SerialName("InvoiceData")
data class InvoiceData(
    @SerialName("invoice_data_encoded_payment_request")
    override val encodedPaymentRequest: String,
    @SerialName("invoice_data_bitcoin_network")
    override val bitcoinNetwork: BitcoinNetwork,
    @SerialName("invoice_data_payment_hash")
    val paymentHash: String,
    @SerialName("invoice_data_amount")
    val amount: CurrencyAmount,
    @SerialName("invoice_data_created_at")
    val createdAt: Instant,
    @SerialName("invoice_data_expires_at")
    val expiresAt: Instant,
    @SerialName("invoice_data_destination")
    val destination: GraphNode,
    @SerialName("invoice_data_memo")
    val memo: String? = null,
) : PaymentRequestData {
    companion object {
        const val FRAGMENT = """
fragment InvoiceDataFragment on InvoiceData {
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
    invoice_data_destination: destination {
        type: __typename
        graph_node_id: id
        graph_node_created_at: created_at
        graph_node_updated_at: updated_at
        graph_node_alias: alias
        graph_node_bitcoin_network: bitcoin_network
        graph_node_color: color
        graph_node_conductivity: conductivity
        graph_node_display_name: display_name
        graph_node_public_key: public_key
    }
}"""
    }
}
