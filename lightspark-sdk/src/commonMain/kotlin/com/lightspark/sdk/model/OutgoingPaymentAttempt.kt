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
 * An attempt for a payment over a route from sender node to recipient node.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the attempt was initiated.
 * @param updatedAt The date and time when the entity was last updated.
 * @param status The status of an outgoing payment attempt.
 * @param outgoingPaymentId The outgoing payment for this attempt.
 * @param failureCode If the payment attempt failed, then this contains the Bolt #4 failure code.
 * @param failureSourceIndex If the payment attempt failed, then this contains the index of the hop at which the problem occurred.
 * @param resolvedAt The time the outgoing payment attempt failed or succeeded.
 * @param amount The total amount of funds required to complete a payment over this route. This value includes the cumulative fees for each hop. As a result, the attempt extended to the first-hop in the route will need to have at least this much value, otherwise the route will fail at an intermediate node due to an insufficient amount.
 * @param fees The sum of the fees paid at each hop within the route of this attempt. In the case of a one-hop payment, this value will be zero as we don't need to pay a fee to ourselves.
 */
@Serializable
@SerialName("OutgoingPaymentAttempt")
data class OutgoingPaymentAttempt(

    @SerialName("outgoing_payment_attempt_id")
    override val id: String,
    @SerialName("outgoing_payment_attempt_created_at")
    override val createdAt: Instant,
    @SerialName("outgoing_payment_attempt_updated_at")
    override val updatedAt: Instant,
    @SerialName("outgoing_payment_attempt_status")
    val status: OutgoingPaymentAttemptStatus,
    @SerialName("outgoing_payment_attempt_outgoing_payment")
    val outgoingPaymentId: EntityId,
    @SerialName("outgoing_payment_attempt_failure_code")
    val failureCode: HtlcAttemptFailureCode? = null,
    @SerialName("outgoing_payment_attempt_failure_source_index")
    val failureSourceIndex: Int? = null,
    @SerialName("outgoing_payment_attempt_resolved_at")
    val resolvedAt: Instant? = null,
    @SerialName("outgoing_payment_attempt_amount")
    val amount: CurrencyAmount? = null,
    @SerialName("outgoing_payment_attempt_fees")
    val fees: CurrencyAmount? = null,
) : Entity {
    @JvmOverloads
    fun getHopsQuery(first: Int? = null): Query<OutgoingPaymentAttemptToHopsConnection> {
        return Query(
            queryPayload = """
query FetchOutgoingPaymentAttemptToHopsConnection(${'$'}entity_id: ID!, ${'$'}first: Int) {
    entity(id: ${'$'}entity_id) {
        ... on OutgoingPaymentAttempt {
            hops(, first: ${'$'}first) {
                type: __typename
                outgoing_payment_attempt_to_hops_connection_count: count
                outgoing_payment_attempt_to_hops_connection_entities: entities {
                    type: __typename
                    hop_id: id
                    hop_created_at: created_at
                    hop_updated_at: updated_at
                    hop_destination: destination {
                        id
                    }
                    hop_index: index
                    hop_public_key: public_key
                    hop_amount_to_forward: amount_to_forward {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    hop_fee: fee {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    hop_expiry_block_height: expiry_block_height
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
            val connection = requireNotNull(it["entity"]?.jsonObject?.get("hops")) { "hops not found" }
            return@Query serializerFormat.decodeFromJsonElement<OutgoingPaymentAttemptToHopsConnection>(connection)
        }
    }

    companion object {
        @JvmStatic
        fun getOutgoingPaymentAttemptQuery(id: String): Query<OutgoingPaymentAttempt> {
            return Query(
                queryPayload = """
query GetOutgoingPaymentAttempt(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on OutgoingPaymentAttempt {
            ...OutgoingPaymentAttemptFragment
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
fragment OutgoingPaymentAttemptFragment on OutgoingPaymentAttempt {
    type: __typename
    outgoing_payment_attempt_id: id
    outgoing_payment_attempt_created_at: created_at
    outgoing_payment_attempt_updated_at: updated_at
    outgoing_payment_attempt_status: status
    outgoing_payment_attempt_failure_code: failure_code
    outgoing_payment_attempt_failure_source_index: failure_source_index
    outgoing_payment_attempt_resolved_at: resolved_at
    outgoing_payment_attempt_amount: amount {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    outgoing_payment_attempt_fees: fees {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    outgoing_payment_attempt_outgoing_payment: outgoing_payment {
        id
    }
}"""
    }
}
