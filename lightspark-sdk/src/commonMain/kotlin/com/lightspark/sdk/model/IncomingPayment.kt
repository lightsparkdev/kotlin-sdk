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
 * This object represents any payment sent to a Lightspark node on the Lightning Network. You can retrieve this object to receive payment related information about a specific payment received by a Lightspark node.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when this transaction was initiated.
 * @param updatedAt The date and time when the entity was last updated.
 * @param status The current status of this transaction.
 * @param amount The amount of money involved in this transaction.
 * @param destinationId The recipient Lightspark node this payment was sent to.
 * @param resolvedAt The date and time when this transaction was completed or failed.
 * @param transactionHash The hash of this transaction, so it can be uniquely identified on the Lightning Network.
 * @param originId If known, the Lightspark node this payment originated from.
 * @param paymentRequestId The optional payment request for this incoming payment, which will be null if the payment is sent through keysend.
 */
@Serializable
@SerialName("IncomingPayment")
data class IncomingPayment(
    @SerialName("incoming_payment_id")
    override val id: String,
    @SerialName("incoming_payment_created_at")
    override val createdAt: Instant,
    @SerialName("incoming_payment_updated_at")
    override val updatedAt: Instant,
    @SerialName("incoming_payment_status")
    override val status: TransactionStatus,
    @SerialName("incoming_payment_amount")
    override val amount: CurrencyAmount,
    @SerialName("incoming_payment_destination")
    val destinationId: EntityId,
    @SerialName("incoming_payment_resolved_at")
    override val resolvedAt: Instant? = null,
    @SerialName("incoming_payment_transaction_hash")
    override val transactionHash: String? = null,
    @SerialName("incoming_payment_origin")
    val originId: EntityId? = null,
    @SerialName("incoming_payment_payment_request")
    val paymentRequestId: EntityId? = null,
) : LightningTransaction, Transaction, Entity {
    @JvmOverloads
    fun getAttemptsQuery(
        first: Int? = null,
        statuses: List<IncomingPaymentAttemptStatus>? = null,
        after: String? = null,
    ): Query<IncomingPaymentToAttemptsConnection> {
        return Query(
            queryPayload = """
query FetchIncomingPaymentToAttemptsConnection(${'$'}entity_id: ID!, ${'$'}first: Int, ${'$'}statuses: [IncomingPaymentAttemptStatus!], ${'$'}after: String) {
    entity(id: ${'$'}entity_id) {
        ... on IncomingPayment {
            attempts(, first: ${'$'}first, statuses: ${'$'}statuses, after: ${'$'}after) {
                type: __typename
                incoming_payment_to_attempts_connection_count: count
                incoming_payment_to_attempts_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
                incoming_payment_to_attempts_connection_entities: entities {
                    type: __typename
                    incoming_payment_attempt_id: id
                    incoming_payment_attempt_created_at: created_at
                    incoming_payment_attempt_updated_at: updated_at
                    incoming_payment_attempt_status: status
                    incoming_payment_attempt_resolved_at: resolved_at
                    incoming_payment_attempt_amount: amount {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    incoming_payment_attempt_channel: channel {
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
                add("statuses", statuses)
                add("after", after)
            }
        ) {
            val connection = requireNotNull(it["entity"]?.jsonObject?.get("attempts")) { "attempts not found" }
            return@Query serializerFormat.decodeFromJsonElement<IncomingPaymentToAttemptsConnection>(connection)
        }
    }

    companion object {
        @JvmStatic
        fun getIncomingPaymentQuery(id: String): Query<IncomingPayment> {
            return Query(
                queryPayload = """
query GetIncomingPayment(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on IncomingPayment {
            ...IncomingPaymentFragment
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
fragment IncomingPaymentFragment on IncomingPayment {
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
}"""
    }
}
