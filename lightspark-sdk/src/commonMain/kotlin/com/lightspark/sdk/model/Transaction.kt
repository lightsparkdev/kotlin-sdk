// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * This object represents a payment transaction. The transaction can occur either on a Bitcoin Network, or over the Lightning Network. You can retrieve this object to receive specific information about a particular transaction tied to your Lightspark Node.
 * @property id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @property createdAt The date and time when this transaction was initiated.
 * @property updatedAt The date and time when the entity was last updated.
 * @property status The current status of this transaction.
 * @property amount The amount of money involved in this transaction.
 * @property resolvedAt The date and time when this transaction was completed or failed.
 * @property transactionHash The hash of this transaction, so it can be uniquely identified on the Lightning Network.
 */
interface Transaction : Entity {

    @SerialName("transaction_id")
    override val id: String

    @SerialName("transaction_created_at")
    override val createdAt: Instant

    @SerialName("transaction_updated_at")
    override val updatedAt: Instant

    @SerialName("transaction_status")
    val status: TransactionStatus

    @SerialName("transaction_amount")
    val amount: CurrencyAmount

    @SerialName("transaction_resolved_at")
    val resolvedAt: Instant?

    @SerialName("transaction_transaction_hash")
    val transactionHash: String?

    companion object {
        @JvmStatic
        fun getTransactionQuery(id: String): Query<Transaction> {
            return Query(
                queryPayload = """
query GetTransaction(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on Transaction {
            ...TransactionFragment
        }
    }
}

$FRAGMENT
""",
                variableBuilder = { add("id", id) },
            ) {
                val entity = requireNotNull(it["entity"]) { "Entity not found" }
                serializerFormat.decodeFromJsonElement(entity)
            }
        }

        const val FRAGMENT = """
fragment TransactionFragment on Transaction {
    type: __typename
    ... on ChannelClosingTransaction {
        type: __typename
        channel_closing_transaction_id: id
        channel_closing_transaction_created_at: created_at
        channel_closing_transaction_updated_at: updated_at
        channel_closing_transaction_status: status
        channel_closing_transaction_resolved_at: resolved_at
        channel_closing_transaction_amount: amount {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        channel_closing_transaction_transaction_hash: transaction_hash
        channel_closing_transaction_fees: fees {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        channel_closing_transaction_block_hash: block_hash
        channel_closing_transaction_block_height: block_height
        channel_closing_transaction_destination_addresses: destination_addresses
        channel_closing_transaction_num_confirmations: num_confirmations
        channel_closing_transaction_channel: channel {
            id
        }
    }
    ... on ChannelOpeningTransaction {
        type: __typename
        channel_opening_transaction_id: id
        channel_opening_transaction_created_at: created_at
        channel_opening_transaction_updated_at: updated_at
        channel_opening_transaction_status: status
        channel_opening_transaction_resolved_at: resolved_at
        channel_opening_transaction_amount: amount {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        channel_opening_transaction_transaction_hash: transaction_hash
        channel_opening_transaction_fees: fees {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        channel_opening_transaction_block_hash: block_hash
        channel_opening_transaction_block_height: block_height
        channel_opening_transaction_destination_addresses: destination_addresses
        channel_opening_transaction_num_confirmations: num_confirmations
        channel_opening_transaction_channel: channel {
            id
        }
    }
    ... on Deposit {
        type: __typename
        deposit_id: id
        deposit_created_at: created_at
        deposit_updated_at: updated_at
        deposit_status: status
        deposit_resolved_at: resolved_at
        deposit_amount: amount {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        deposit_transaction_hash: transaction_hash
        deposit_fees: fees {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        deposit_block_hash: block_hash
        deposit_block_height: block_height
        deposit_destination_addresses: destination_addresses
        deposit_num_confirmations: num_confirmations
        deposit_destination: destination {
            id
        }
    }
    ... on IncomingPayment {
        type: __typename
        incoming_payment_id: id
        incoming_payment_created_at: created_at
        incoming_payment_updated_at: updated_at
        incoming_payment_status: status
        incoming_payment_resolved_at: resolved_at
        incoming_payment_amount: amount {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        incoming_payment_transaction_hash: transaction_hash
        incoming_payment_origin: origin {
            id
        }
        incoming_payment_destination: destination {
            id
        }
        incoming_payment_payment_request: payment_request {
            id
        }
    }
    ... on OutgoingPayment {
        type: __typename
        outgoing_payment_id: id
        outgoing_payment_created_at: created_at
        outgoing_payment_updated_at: updated_at
        outgoing_payment_status: status
        outgoing_payment_resolved_at: resolved_at
        outgoing_payment_amount: amount {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        outgoing_payment_transaction_hash: transaction_hash
        outgoing_payment_origin: origin {
            id
        }
        outgoing_payment_destination: destination {
            id
        }
        outgoing_payment_fees: fees {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        outgoing_payment_payment_request_data: payment_request_data {
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
                        lightspark_node_owner: owner {
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
            }
        }
        outgoing_payment_failure_reason: failure_reason
        outgoing_payment_failure_message: failure_message {
            type: __typename
            rich_text_text: text
        }
    }
    ... on RoutingTransaction {
        type: __typename
        routing_transaction_id: id
        routing_transaction_created_at: created_at
        routing_transaction_updated_at: updated_at
        routing_transaction_status: status
        routing_transaction_resolved_at: resolved_at
        routing_transaction_amount: amount {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        routing_transaction_transaction_hash: transaction_hash
        routing_transaction_incoming_channel: incoming_channel {
            id
        }
        routing_transaction_outgoing_channel: outgoing_channel {
            id
        }
        routing_transaction_fees: fees {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        routing_transaction_failure_message: failure_message {
            type: __typename
            rich_text_text: text
        }
        routing_transaction_failure_reason: failure_reason
    }
    ... on Withdrawal {
        type: __typename
        withdrawal_id: id
        withdrawal_created_at: created_at
        withdrawal_updated_at: updated_at
        withdrawal_status: status
        withdrawal_resolved_at: resolved_at
        withdrawal_amount: amount {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        withdrawal_transaction_hash: transaction_hash
        withdrawal_fees: fees {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        withdrawal_block_hash: block_hash
        withdrawal_block_height: block_height
        withdrawal_destination_addresses: destination_addresses
        withdrawal_num_confirmations: num_confirmations
        withdrawal_origin: origin {
            id
        }
    }
}"""
    }
}
