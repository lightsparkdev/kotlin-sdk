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
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * This is an object representing a channel on the Lightning Network. You can retrieve this object to get detailed information on a specific Lightning Network channel.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param localNodeId The local Lightspark node of the channel.
 * @param fundingTransactionId The transaction that funded the channel upon channel opening.
 * @param capacity The total amount of funds in this channel, including the channel balance on the local node, the channel balance on the remote node and the on-chain fees to close the channel.
 * @param localBalance The channel balance on the local node.
 * @param localUnsettledBalance The channel balance on the local node that is currently allocated to in-progress payments.
 * @param remoteBalance The channel balance on the remote node.
 * @param remoteUnsettledBalance The channel balance on the remote node that is currently allocated to in-progress payments.
 * @param unsettledBalance The channel balance that is currently allocated to in-progress payments.
 * @param totalBalance The total balance in this channel, including the channel balance on both local and remote nodes.
 * @param status The current status of this channel.
 * @param estimatedForceClosureWaitMinutes The estimated time to wait for the channel's hash timelock contract to expire when force closing the channel. It is in unit of minutes.
 * @param commitFee The amount to be paid in fees for the current set of commitment transactions.
 * @param fees The fees charged for routing payments through this channel.
 * @param remoteNodeId If known, the remote node of the channel.
 * @param shortChannelId The unique identifier of the channel on Lightning Network, which is the location in the chain that the channel was confirmed. The format is <block-height>:<tx-index>:<tx-output>.
 */
@Serializable
@SerialName("Channel")
data class Channel(
    @SerialName("channel_id")
    override val id: String,
    @SerialName("channel_created_at")
    override val createdAt: Instant,
    @SerialName("channel_updated_at")
    override val updatedAt: Instant,
    @SerialName("channel_local_node")
    val localNodeId: EntityId,
    @SerialName("channel_funding_transaction")
    val fundingTransactionId: EntityId? = null,
    @SerialName("channel_capacity")
    val capacity: CurrencyAmount? = null,
    @SerialName("channel_local_balance")
    val localBalance: CurrencyAmount? = null,
    @SerialName("channel_local_unsettled_balance")
    val localUnsettledBalance: CurrencyAmount? = null,
    @SerialName("channel_remote_balance")
    val remoteBalance: CurrencyAmount? = null,
    @SerialName("channel_remote_unsettled_balance")
    val remoteUnsettledBalance: CurrencyAmount? = null,
    @SerialName("channel_unsettled_balance")
    val unsettledBalance: CurrencyAmount? = null,
    @SerialName("channel_total_balance")
    val totalBalance: CurrencyAmount? = null,
    @SerialName("channel_status")
    val status: ChannelStatus? = null,
    @SerialName("channel_estimated_force_closure_wait_minutes")
    val estimatedForceClosureWaitMinutes: Int? = null,
    @SerialName("channel_commit_fee")
    val commitFee: CurrencyAmount? = null,
    @SerialName("channel_fees")
    val fees: ChannelFees? = null,
    @SerialName("channel_remote_node")
    val remoteNodeId: EntityId? = null,
    @SerialName("channel_short_channel_id")
    val shortChannelId: String? = null,
) : Entity {
    @JvmOverloads
    fun getUptimePercentageQuery(afterDate: Instant? = null, beforeDate: Instant? = null): Query<Int?> {
        return Query(
            queryPayload = """
query FetchChannelUptimePercentage(${'$'}entity_id: ID!, ${'$'}after_date: DateTime, ${'$'}before_date: DateTime) {
    entity(id: ${'$'}entity_id) {
        ... on Channel {
            uptime_percentage(, after_date: ${'$'}after_date, before_date: ${'$'}before_date)
        }
    }
}
""",
            variableBuilder = {
                add("entity_id", id)
                add("after_date", afterDate)
                add("before_date", beforeDate)
            }
        ) {
            val connection = it["entity"]?.jsonObject?.get("uptime_percentage") ?: return@Query null
            return@Query connection.jsonPrimitive.int
        }
    }

    @JvmOverloads
    fun getTransactionsQuery(
        types: List<TransactionType>? = null,
        afterDate: Instant? = null,
        beforeDate: Instant? = null,
    ): Query<ChannelToTransactionsConnection> {
        return Query(
            queryPayload = """
query FetchChannelToTransactionsConnection(${'$'}entity_id: ID!, ${'$'}types: [TransactionType!], ${'$'}after_date: DateTime, ${'$'}before_date: DateTime) {
    entity(id: ${'$'}entity_id) {
        ... on Channel {
            transactions(, types: ${'$'}types, after_date: ${'$'}after_date, before_date: ${'$'}before_date) {
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
            }
        }
    }
}
""",
            variableBuilder = {
                add("entity_id", id)
                add("types", types)
                add("after_date", afterDate)
                add("before_date", beforeDate)
            }
        ) {
            val connection =
                requireNotNull(it["entity"]?.jsonObject?.get("transactions")) { "transactions not found" }
            return@Query serializerFormat.decodeFromJsonElement<ChannelToTransactionsConnection>(connection)
        }
    }

    companion object {
        @JvmStatic
        fun getChannelQuery(id: String): Query<Channel> {
            return Query(
                queryPayload = """
query GetChannel(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on Channel {
            ...ChannelFragment
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
fragment ChannelFragment on Channel {
    type: __typename
    channel_id: id
    channel_created_at: created_at
    channel_updated_at: updated_at
    channel_funding_transaction: funding_transaction {
        id
    }
    channel_capacity: capacity {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_local_balance: local_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_local_unsettled_balance: local_unsettled_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_remote_balance: remote_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_remote_unsettled_balance: remote_unsettled_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_unsettled_balance: unsettled_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_total_balance: total_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_status: status
    channel_estimated_force_closure_wait_minutes: estimated_force_closure_wait_minutes
    channel_commit_fee: commit_fee {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_fees: fees {
        type: __typename
        channel_fees_base_fee: base_fee {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        channel_fees_fee_rate_per_mil: fee_rate_per_mil
    }
    channel_remote_node: remote_node {
        id
    }
    channel_local_node: local_node {
        id
    }
    channel_short_channel_id: short_channel_id
}"""
    }
}
