// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * This object represents a transaction that was forwarded through a Lightspark node on the Lightning Network, i.e., a routed transaction. You can retrieve this object to receive information about any transaction routed through your Lightspark Node.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when this transaction was initiated.
 * @param updatedAt The date and time when the entity was last updated.
 * @param status The current status of this transaction.
 * @param amount The amount of money involved in this transaction.
 * @param resolvedAt The date and time when this transaction was completed or failed.
 * @param transactionHash The hash of this transaction, so it can be uniquely identified on the Lightning Network.
 * @param incomingChannelId If known, the channel this transaction was received from.
 * @param outgoingChannelId If known, the channel this transaction was forwarded to.
 * @param fees The fees collected by the node when routing this transaction. We subtract the outgoing amount to the incoming amount to determine how much fees were collected.
 * @param failureMessage If applicable, user-facing error message describing why the routing failed.
 * @param failureReason If applicable, the reason why the routing failed.
 */
@Serializable
@SerialName("RoutingTransaction")
data class RoutingTransaction(
    @SerialName("routing_transaction_id")
    override val id: String,
    @SerialName("routing_transaction_created_at")
    override val createdAt: Instant,
    @SerialName("routing_transaction_updated_at")
    override val updatedAt: Instant,
    @SerialName("routing_transaction_status")
    override val status: TransactionStatus,
    @SerialName("routing_transaction_amount")
    override val amount: CurrencyAmount,
    @SerialName("routing_transaction_resolved_at")
    override val resolvedAt: Instant? = null,
    @SerialName("routing_transaction_transaction_hash")
    override val transactionHash: String? = null,
    @SerialName("routing_transaction_incoming_channel")
    val incomingChannelId: EntityId? = null,
    @SerialName("routing_transaction_outgoing_channel")
    val outgoingChannelId: EntityId? = null,
    @SerialName("routing_transaction_fees")
    val fees: CurrencyAmount? = null,
    @SerialName("routing_transaction_failure_message")
    val failureMessage: RichText? = null,
    @SerialName("routing_transaction_failure_reason")
    val failureReason: RoutingTransactionFailureReason? = null,
) : LightningTransaction, Transaction, Entity {
    companion object {
        @JvmStatic
        fun getRoutingTransactionQuery(id: String): Query<RoutingTransaction> {
            return Query(
                queryPayload = """
query GetRoutingTransaction(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on RoutingTransaction {
            ...RoutingTransactionFragment
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
fragment RoutingTransactionFragment on RoutingTransaction {
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
}"""
    }
}
