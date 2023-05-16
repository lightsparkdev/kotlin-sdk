// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.wallet.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * This object represents a BOLT #11 invoice (https://github.com/lightning/bolts/blob/master/11-payment-encoding.md) initiated by a Lightspark Node.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param data The details of the invoice.
 * @param status The status of the payment request.
 * @param amountPaid The total amount that has been paid to this invoice.
 */
@Serializable
@SerialName("Invoice")
data class Invoice(

    @SerialName("invoice_id")
    override val id: String,
    @SerialName("invoice_created_at")
    override val createdAt: Instant,
    @SerialName("invoice_updated_at")
    override val updatedAt: Instant,
    @SerialName("invoice_data")
    override val data: InvoiceData,
    @SerialName("invoice_status")
    override val status: PaymentRequestStatus,
    @SerialName("invoice_amount_paid")
    val amountPaid: CurrencyAmount? = null,
) : PaymentRequest, Entity {

    companion object {
        @JvmStatic
        fun getInvoiceQuery(id: String): Query<Invoice> {
            return Query(
                queryPayload = """
query GetInvoice(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on Invoice {
            ...InvoiceFragment
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
fragment InvoiceFragment on Invoice {
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
}"""
    }
}
