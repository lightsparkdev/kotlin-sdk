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
 *
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param amount The amount of money that should be withdrawn in this request.
 * @param bitcoinAddress The bitcoin address where the funds should be sent.
 * @param status The current status of this withdrawal request.
 * @param estimatedAmount If the requested amount is `-1` (i.e. everything), this field may contain an estimate of the amount for the withdrawal.
 * @param completedAt The time at which this request was completed.
 * @param withdrawalId The withdrawal transaction that has been generated by this request.
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
    @SerialName("withdrawal_request_amount")
    val amount: CurrencyAmount,
    @SerialName("withdrawal_request_bitcoin_address")
    val bitcoinAddress: String,
    @SerialName("withdrawal_request_status")
    val status: WithdrawalRequestStatus,
    @SerialName("withdrawal_request_estimated_amount")
    val estimatedAmount: CurrencyAmount? = null,
    @SerialName("withdrawal_request_completed_at")
    val completedAt: Instant? = null,
    @SerialName("withdrawal_request_withdrawal")
    val withdrawalId: EntityId? = null,
) : Entity {

    companion object {
        @JvmStatic
        fun getWithdrawalRequestQuery(id: String): Query<WithdrawalRequest> {
            return Query(
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
        }

        const val FRAGMENT = """
fragment WithdrawalRequestFragment on WithdrawalRequest {
    type: __typename
    withdrawal_request_id: id
    withdrawal_request_created_at: created_at
    withdrawal_request_updated_at: updated_at
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
    withdrawal_request_bitcoin_address: bitcoin_address
    withdrawal_request_status: status
    withdrawal_request_completed_at: completed_at
    withdrawal_request_withdrawal: withdrawal {
        id
    }
}"""
    }
}
