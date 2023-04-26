// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.wallet.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.decodeFromJsonElement

/**
 *
 * @property id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @property createdAt The date and time when the entity was first created.
 * @property updatedAt The date and time when the entity was last updated.
 * @property data The details of the payment request.
 * @property status The status of the payment request.
 */
interface PaymentRequest : Entity {

    @SerialName("payment_request_id")
    override val id: String

    @SerialName("payment_request_created_at")
    override val createdAt: Instant

    @SerialName("payment_request_updated_at")
    override val updatedAt: Instant

    @SerialName("payment_request_data")
    val data: PaymentRequestData

    @SerialName("payment_request_status")
    val status: PaymentRequestStatus

    companion object {
        @JvmStatic
        fun getPaymentRequestQuery(id: String): Query<PaymentRequest> {
            return Query(
                queryPayload = """
query GetPaymentRequest(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on PaymentRequest {
            ...PaymentRequestFragment
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
fragment PaymentRequestFragment on PaymentRequest {
    type: __typename
    ... on Invoice {
        type: __typename
        invoice_id: id
        invoice_created_at: created_at
        invoice_updated_at: updated_at
        invoice_data: data {
            type: __typename
            invoice_data_encoded_payment_request: encoded_payment_request
            invoice_data_bitcoin_network: bitcoin_network
            invoice_data_payment_hash: payment_hash
            invoice_data_amount: amount {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            invoice_data_created_at: created_at
            invoice_data_expires_at: expires_at
            invoice_data_memo: memo
        }
        invoice_status: status
        invoice_amount_paid: amount_paid {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
    }
}"""
    }
}