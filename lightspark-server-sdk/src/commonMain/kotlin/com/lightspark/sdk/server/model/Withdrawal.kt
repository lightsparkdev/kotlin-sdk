// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.server.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * The transaction on the Bitcoin blockchain to withdraw funds from the Lightspark node to a Bitcoin wallet.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when this transaction was initiated.
 * @param updatedAt The date and time when the entity was last updated.
 * @param status The current status of this transaction.
 * @param amount The amount of money involved in this transaction.
 * @param blockHeight The height of the block that included this transaction. This will be zero for unconfirmed transactions.
 * @param destinationAddresses The Bitcoin blockchain addresses this transaction was sent to.
 * @param originId The Lightspark node this withdrawal originated from.
 * @param resolvedAt The date and time when this transaction was completed or failed.
 * @param transactionHash The hash of this transaction, so it can be uniquely identified on the Lightning Network.
 * @param fees The fees that were paid by the wallet sending the transaction to commit it to the Bitcoin blockchain.
 * @param blockHash The hash of the block that included this transaction. This will be null for unconfirmed transactions.
 * @param numConfirmations The number of blockchain confirmations for this transaction in real time.
 */
@Serializable
@SerialName("Withdrawal")
data class Withdrawal(

    @SerialName("withdrawal_id")
    override val id: String,
    @SerialName("withdrawal_created_at")
    override val createdAt: Instant,
    @SerialName("withdrawal_updated_at")
    override val updatedAt: Instant,
    @SerialName("withdrawal_status")
    override val status: TransactionStatus,
    @SerialName("withdrawal_amount")
    override val amount: CurrencyAmount,
    @SerialName("withdrawal_block_height")
    override val blockHeight: Int,
    @SerialName("withdrawal_destination_addresses")
    override val destinationAddresses: List<String>,
    @SerialName("withdrawal_origin")
    val originId: EntityId,
    @SerialName("withdrawal_resolved_at")
    override val resolvedAt: Instant? = null,
    @SerialName("withdrawal_transaction_hash")
    override val transactionHash: String? = null,
    @SerialName("withdrawal_fees")
    override val fees: CurrencyAmount? = null,
    @SerialName("withdrawal_block_hash")
    override val blockHash: String? = null,
    @SerialName("withdrawal_num_confirmations")
    override val numConfirmations: Int? = null,
) : OnChainTransaction, Transaction, Entity {

    companion object {
        @JvmStatic
        fun getWithdrawalQuery(id: String): Query<Withdrawal> {
            return Query(
                queryPayload = """
query GetWithdrawal(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on Withdrawal {
            ...WithdrawalFragment
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
fragment WithdrawalFragment on Withdrawal {
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
}"""
    }
}
