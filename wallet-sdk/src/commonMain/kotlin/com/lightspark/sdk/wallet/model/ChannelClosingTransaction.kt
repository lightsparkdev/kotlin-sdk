// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.wallet.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * This is an object representing a transaction which closes a channel on the Lightning Network. This operation allocates balances back to the local and remote nodes.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when this transaction was initiated.
 * @param updatedAt The date and time when the entity was last updated.
 * @param status The current status of this transaction.
 * @param amount The amount of money involved in this transaction.
 * @param blockHeight The height of the block that included this transaction. This will be zero for unconfirmed transactions.
 * @param destinationAddresses The Bitcoin blockchain addresses this transaction was sent to.
 * @param resolvedAt The date and time when this transaction was completed or failed.
 * @param transactionHash The hash of this transaction, so it can be uniquely identified on the Lightning Network.
 * @param fees The fees that were paid by the node for this transaction.
 * @param blockHash The hash of the block that included this transaction. This will be null for unconfirmed transactions.
 * @param numConfirmations The number of blockchain confirmations for this transaction in real time.
 */
@Serializable
@SerialName("ChannelClosingTransaction")
data class ChannelClosingTransaction(
    @SerialName("channel_closing_transaction_id")
    override val id: String,
    @SerialName("channel_closing_transaction_created_at")
    override val createdAt: Instant,
    @SerialName("channel_closing_transaction_updated_at")
    override val updatedAt: Instant,
    @SerialName("channel_closing_transaction_status")
    override val status: TransactionStatus,
    @SerialName("channel_closing_transaction_amount")
    override val amount: CurrencyAmount,
    @SerialName("channel_closing_transaction_block_height")
    override val blockHeight: Int,
    @SerialName("channel_closing_transaction_destination_addresses")
    override val destinationAddresses: List<String>,
    @SerialName("channel_closing_transaction_resolved_at")
    override val resolvedAt: Instant? = null,
    @SerialName("channel_closing_transaction_transaction_hash")
    override val transactionHash: String? = null,
    @SerialName("channel_closing_transaction_fees")
    override val fees: CurrencyAmount? = null,
    @SerialName("channel_closing_transaction_block_hash")
    override val blockHash: String? = null,
    @SerialName("channel_closing_transaction_num_confirmations")
    override val numConfirmations: Int? = null,
) : OnChainTransaction,
    Transaction,
    Entity {
    companion object {
        @JvmStatic
        fun getChannelClosingTransactionQuery(id: String): Query<ChannelClosingTransaction> = Query(
            queryPayload = """
query GetChannelClosingTransaction(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on ChannelClosingTransaction {
            ...ChannelClosingTransactionFragment
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
fragment ChannelClosingTransactionFragment on ChannelClosingTransaction {
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
}"""
    }
}
