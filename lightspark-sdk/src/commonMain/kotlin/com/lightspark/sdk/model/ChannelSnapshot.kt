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
 *
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param timestamp The timestamp that was used to query the snapshot of the channel
 */
@Serializable
@SerialName("ChannelSnapshot")
data class ChannelSnapshot(
    @SerialName("channel_snapshot_id")
    override val id: String,
    @SerialName("channel_snapshot_created_at")
    override val createdAt: Instant,
    @SerialName("channel_snapshot_updated_at")
    override val updatedAt: Instant,
    @SerialName("channel_snapshot_channel")
    val channelId: EntityId,
    @SerialName("channel_snapshot_timestamp")
    val timestamp: Instant,
    @SerialName("channel_snapshot_local_balance")
    val localBalance: CurrencyAmount? = null,
    @SerialName("channel_snapshot_local_unsettled_balance")
    val localUnsettledBalance: CurrencyAmount? = null,
    @SerialName("channel_snapshot_remote_balance")
    val remoteBalance: CurrencyAmount? = null,
    @SerialName("channel_snapshot_remote_unsettled_balance")
    val remoteUnsettledBalance: CurrencyAmount? = null,
    @SerialName("channel_snapshot_local_channel_reserve")
    val localChannelReserve: CurrencyAmount? = null,
) : Entity {
    companion object {
        @JvmStatic
        fun getChannelSnapshotQuery(id: String): Query<ChannelSnapshot> {
            return Query(
                queryPayload = """
query GetChannelSnapshot(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on ChannelSnapshot {
            ...ChannelSnapshotFragment
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
fragment ChannelSnapshotFragment on ChannelSnapshot {
    type: __typename
    channel_snapshot_id: id
    channel_snapshot_created_at: created_at
    channel_snapshot_updated_at: updated_at
    channel_snapshot_local_balance: local_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_snapshot_local_unsettled_balance: local_unsettled_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_snapshot_remote_balance: remote_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_snapshot_remote_unsettled_balance: remote_unsettled_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_snapshot_channel: channel {
        id
    }
    channel_snapshot_local_channel_reserve: local_channel_reserve {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_snapshot_timestamp: timestamp
}"""
    }
}
