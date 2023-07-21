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
 * The transaction on Bitcoin blockchain to open a channel on Lightning Network funded by the local Lightspark node.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when this transaction was initiated.
 * @param updatedAt The date and time when the entity was last updated.
 * @param status The current status of this transaction.
 * @param amount The amount of money involved in this transaction.
 * @param blockHeight The height of the block that included this transaction. This will be zero for unconfirmed transactions.
 * @param destinationAddresses The Bitcoin blockchain addresses this transaction was sent to.
 * @param resolvedAt The date and time when this transaction was completed or failed.
 * @param transactionHash The hash of this transaction, so it can be uniquely identified on the Lightning Network.
 * @param fees The fees that were paid by the wallet sending the transaction to commit it to the Bitcoin blockchain.
 * @param blockHash The hash of the block that included this transaction. This will be null for unconfirmed transactions.
 * @param numConfirmations The number of blockchain confirmations for this transaction in real time.
 * @param channelId If known, the channel this transaction is opening.
 */
@Serializable
@SerialName("ChannelOpeningTransaction")
data class ChannelOpeningTransaction(

    @SerialName("channel_opening_transaction_id")
    override val id: String,
    @SerialName("channel_opening_transaction_created_at")
    override val createdAt: Instant,
    @SerialName("channel_opening_transaction_updated_at")
    override val updatedAt: Instant,
    @SerialName("channel_opening_transaction_status")
    override val status: TransactionStatus,
    @SerialName("channel_opening_transaction_amount")
    override val amount: CurrencyAmount,
    @SerialName("channel_opening_transaction_block_height")
    override val blockHeight: Int,
    @SerialName("channel_opening_transaction_destination_addresses")
    override val destinationAddresses: List<String>,
    @SerialName("channel_opening_transaction_resolved_at")
    override val resolvedAt: Instant? = null,
    @SerialName("channel_opening_transaction_transaction_hash")
    override val transactionHash: String? = null,
    @SerialName("channel_opening_transaction_fees")
    override val fees: CurrencyAmount? = null,
    @SerialName("channel_opening_transaction_block_hash")
    override val blockHash: String? = null,
    @SerialName("channel_opening_transaction_num_confirmations")
    override val numConfirmations: Int? = null,
    @SerialName("channel_opening_transaction_channel")
    val channelId: EntityId? = null,
) : OnChainTransaction, Transaction, Entity {

    companion object {
        @JvmStatic
        fun getChannelOpeningTransactionQuery(id: String): Query<ChannelOpeningTransaction> {
            return Query(
                queryPayload = """
query GetChannelOpeningTransaction(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on ChannelOpeningTransaction {
            ...ChannelOpeningTransactionFragment
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
fragment ChannelOpeningTransactionFragment on ChannelOpeningTransaction {
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
}"""
    }
}
