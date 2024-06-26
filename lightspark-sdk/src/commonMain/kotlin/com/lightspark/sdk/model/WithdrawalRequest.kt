// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.util.serializerFormat
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

/**
 * This object represents a request made for an L1 withdrawal from your Lightspark Node to any Bitcoin wallet. You can retrieve this object to receive detailed information about any withdrawal request made from your Lightspark account.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param requestedAmount The requested amount of money to be withdrawn. If the requested amount is -1, it means to withdraw all.
 * @param amount The amount of money that should be withdrawn in this request.
 * @param bitcoinAddress The bitcoin address where the funds should be sent.
 * @param withdrawalMode The strategy that should be used to withdraw the funds from the account.
 * @param status The current status of this withdrawal request.
 * @param initiator The initiator of the withdrawal.
 * @param estimatedAmount If the requested amount is `-1` (i.e. everything), this field may contain an estimate of the amount for the withdrawal.
 * @param amountWithdrawn The actual amount that is withdrawn to the bitcoin address. It will be set once the request is completed.
 * @param totalFees The total fees the node paid for the withdrawal. It will be set once the request is completed.
 * @param completedAt The time at which this request was completed.
 * @param withdrawalId The withdrawal transaction that has been generated by this request.
 * @param idempotencyKey The idempotency key of the withdrawal request.
 */
@Serializable
@SerialName("WithdrawalRequest")
data class WithdrawalRequest(
    @SerialName("withdrawal_request_id")
    override val id: String,
    @SerialName("withdrawal_request_created_at")
    override val createdAt: Instant,
    @SerialName("withdrawal_request_updated_at")
    override val updatedAt: Instant,
    @SerialName("withdrawal_request_requested_amount")
    val requestedAmount: CurrencyAmount,
    @SerialName("withdrawal_request_amount")
    val amount: CurrencyAmount,
    @SerialName("withdrawal_request_bitcoin_address")
    val bitcoinAddress: String,
    @SerialName("withdrawal_request_withdrawal_mode")
    val withdrawalMode: WithdrawalMode,
    @SerialName("withdrawal_request_status")
    val status: WithdrawalRequestStatus,
    @SerialName("withdrawal_request_initiator")
    val initiator: RequestInitiator,
    @SerialName("withdrawal_request_estimated_amount")
    val estimatedAmount: CurrencyAmount? = null,
    @SerialName("withdrawal_request_amount_withdrawn")
    val amountWithdrawn: CurrencyAmount? = null,
    @SerialName("withdrawal_request_total_fees")
    val totalFees: CurrencyAmount? = null,
    @SerialName("withdrawal_request_completed_at")
    val completedAt: Instant? = null,
    @SerialName("withdrawal_request_withdrawal")
    val withdrawalId: EntityId? = null,
    @SerialName("withdrawal_request_idempotency_key")
    val idempotencyKey: String? = null,
) : Entity {
    @JvmOverloads
    fun getChannelClosingTransactionsQuery(
        first: Int? = null,
        after: String? = null,
    ): Query<WithdrawalRequestToChannelClosingTransactionsConnection> {
        return Query(
            queryPayload = """
query FetchWithdrawalRequestToChannelClosingTransactionsConnection(${'$'}entity_id: ID!, ${'$'}first: Int, ${'$'}after: String) {
    entity(id: ${'$'}entity_id) {
        ... on WithdrawalRequest {
            channel_closing_transactions(, first: ${'$'}first, after: ${'$'}after) {
                type: __typename
                withdrawal_request_to_channel_closing_transactions_connection_count: count
                withdrawal_request_to_channel_closing_transactions_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
                withdrawal_request_to_channel_closing_transactions_connection_entities: entities {
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
            }
        }
    }
}
""",
            variableBuilder = {
                add("entity_id", id)
                add("first", first)
                add("after", after)
            }
        ) {
            val connection =
                requireNotNull(it["entity"]?.jsonObject?.get("channel_closing_transactions")) {
                    "channel_closing_transactions not found"
                }
            return@Query serializerFormat
                .decodeFromJsonElement<WithdrawalRequestToChannelClosingTransactionsConnection>(
                    connection
                )
        }
    }

    @JvmOverloads
    fun getChannelOpeningTransactionsQuery(
        first: Int? = null,
        after: String? = null,
    ): Query<WithdrawalRequestToChannelOpeningTransactionsConnection> {
        return Query(
            queryPayload = """
query FetchWithdrawalRequestToChannelOpeningTransactionsConnection(${'$'}entity_id: ID!, ${'$'}first: Int, ${'$'}after: String) {
    entity(id: ${'$'}entity_id) {
        ... on WithdrawalRequest {
            channel_opening_transactions(, first: ${'$'}first, after: ${'$'}after) {
                type: __typename
                withdrawal_request_to_channel_opening_transactions_connection_count: count
                withdrawal_request_to_channel_opening_transactions_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
                withdrawal_request_to_channel_opening_transactions_connection_entities: entities {
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
            }
        }
    }
}
""",
            variableBuilder = {
                add("entity_id", id)
                add("first", first)
                add("after", after)
            }
        ) {
            val connection =
                requireNotNull(it["entity"]?.jsonObject?.get("channel_opening_transactions")) {
                    "channel_opening_transactions not found"
                }
            return@Query serializerFormat
                .decodeFromJsonElement<WithdrawalRequestToChannelOpeningTransactionsConnection>(
                    connection
                )
        }
    }

    @JvmOverloads
    fun getWithdrawalsQuery(first: Int? = null): Query<WithdrawalRequestToWithdrawalsConnection> {
        return Query(
            queryPayload = """
query FetchWithdrawalRequestToWithdrawalsConnection(${'$'}entity_id: ID!, ${'$'}first: Int) {
    entity(id: ${'$'}entity_id) {
        ... on WithdrawalRequest {
            withdrawals(, first: ${'$'}first) {
                type: __typename
                withdrawal_request_to_withdrawals_connection_count: count
                withdrawal_request_to_withdrawals_connection_entities: entities {
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
            }
        }
    }
}
""",
            variableBuilder = {
                add("entity_id", id)
                add("first", first)
            }
        ) {
            val connection =
                requireNotNull(it["entity"]?.jsonObject?.get("withdrawals")) { "withdrawals not found" }
            return@Query serializerFormat.decodeFromJsonElement<WithdrawalRequestToWithdrawalsConnection>(
                connection
            )
        }
    }

    companion object {
        @JvmStatic
        fun getWithdrawalRequestQuery(id: String): Query<WithdrawalRequest> = Query(
            queryPayload = """
query GetWithdrawalRequest(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on WithdrawalRequest {
            ...WithdrawalRequestFragment
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

        const val FRAGMENT = """
fragment WithdrawalRequestFragment on WithdrawalRequest {
    type: __typename
    withdrawal_request_id: id
    withdrawal_request_created_at: created_at
    withdrawal_request_updated_at: updated_at
    withdrawal_request_requested_amount: requested_amount {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    withdrawal_request_amount: amount {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    withdrawal_request_estimated_amount: estimated_amount {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    withdrawal_request_amount_withdrawn: amount_withdrawn {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    withdrawal_request_total_fees: total_fees {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    withdrawal_request_bitcoin_address: bitcoin_address
    withdrawal_request_withdrawal_mode: withdrawal_mode
    withdrawal_request_status: status
    withdrawal_request_completed_at: completed_at
    withdrawal_request_withdrawal: withdrawal {
        id
    }
    withdrawal_request_idempotency_key: idempotency_key
    withdrawal_request_initiator: initiator
}"""
    }
}
