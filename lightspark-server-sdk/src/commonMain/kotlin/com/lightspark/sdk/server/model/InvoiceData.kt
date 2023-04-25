// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This object represents the BOLT #11 invoice protocol for Lightning Payments. See https://github.com/lightning/bolts/blob/master/11-payment-encoding.md.
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
    val destination: Node,
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
    invoice_data_destination: destination {
        type: __typename
        ... on GraphNode {
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
        ... on LightsparkNode {
            type: __typename
            lightspark_node_id: id
            lightspark_node_created_at: created_at
            lightspark_node_updated_at: updated_at
            lightspark_node_alias: alias
            lightspark_node_bitcoin_network: bitcoin_network
            lightspark_node_color: color
            lightspark_node_conductivity: conductivity
            lightspark_node_display_name: display_name
            lightspark_node_public_key: public_key
            lightspark_node_account: account {
                id
            }
            lightspark_node_blockchain_balance: blockchain_balance {
                type: __typename
                blockchain_balance_total_balance: total_balance {
                    type: __typename
                    currency_amount_original_value: original_value
                    currency_amount_original_unit: original_unit
                    currency_amount_preferred_currency_unit: preferred_currency_unit
                    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                }
                blockchain_balance_confirmed_balance: confirmed_balance {
                    type: __typename
                    currency_amount_original_value: original_value
                    currency_amount_original_unit: original_unit
                    currency_amount_preferred_currency_unit: preferred_currency_unit
                    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                }
                blockchain_balance_unconfirmed_balance: unconfirmed_balance {
                    type: __typename
                    currency_amount_original_value: original_value
                    currency_amount_original_unit: original_unit
                    currency_amount_preferred_currency_unit: preferred_currency_unit
                    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                }
                blockchain_balance_locked_balance: locked_balance {
                    type: __typename
                    currency_amount_original_value: original_value
                    currency_amount_original_unit: original_unit
                    currency_amount_preferred_currency_unit: preferred_currency_unit
                    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                }
                blockchain_balance_required_reserve: required_reserve {
                    type: __typename
                    currency_amount_original_value: original_value
                    currency_amount_original_unit: original_unit
                    currency_amount_preferred_currency_unit: preferred_currency_unit
                    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                }
                blockchain_balance_available_balance: available_balance {
                    type: __typename
                    currency_amount_original_value: original_value
                    currency_amount_original_unit: original_unit
                    currency_amount_preferred_currency_unit: preferred_currency_unit
                    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                }
            }
            lightspark_node_encrypted_signing_private_key: encrypted_signing_private_key {
                type: __typename
                secret_encrypted_value: encrypted_value
                secret_cipher: cipher
            }
            lightspark_node_total_balance: total_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            lightspark_node_total_local_balance: total_local_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            lightspark_node_local_balance: local_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            lightspark_node_purpose: purpose
            lightspark_node_remote_balance: remote_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            lightspark_node_status: status
        }
    }
    invoice_data_memo: memo
}"""
    }
}
