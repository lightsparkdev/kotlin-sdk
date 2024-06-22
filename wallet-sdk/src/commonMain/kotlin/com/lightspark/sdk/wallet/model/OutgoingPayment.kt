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
 * This object represents a Lightning Network payment sent from a Lightspark Node. You can retrieve this object to receive payment related information about any payment sent from your Lightspark Node on the Lightning Network.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when this transaction was initiated.
 * @param updatedAt The date and time when the entity was last updated.
 * @param status The current status of this transaction.
 * @param amount The amount of money involved in this transaction.
 * @param resolvedAt The date and time when this transaction was completed or failed.
 * @param transactionHash The hash of this transaction, so it can be uniquely identified on the Lightning Network.
 * @param fees The fees paid by the sender node to send the payment.
 * @param paymentRequestData The data of the payment request that was paid by this transaction, if known.
 * @param failureReason If applicable, the reason why the payment failed.
 * @param failureMessage If applicable, user-facing error message describing why the payment failed.
 * @param paymentPreimage The preimage of the payment.
 */
@Serializable
@SerialName("OutgoingPayment")
data class OutgoingPayment(
    @SerialName("outgoing_payment_id")
    override val id: String,
    @SerialName("outgoing_payment_created_at")
    override val createdAt: Instant,
    @SerialName("outgoing_payment_updated_at")
    override val updatedAt: Instant,
    @SerialName("outgoing_payment_status")
    override val status: TransactionStatus,
    @SerialName("outgoing_payment_amount")
    override val amount: CurrencyAmount,
    @SerialName("outgoing_payment_resolved_at")
    override val resolvedAt: Instant? = null,
    @SerialName("outgoing_payment_transaction_hash")
    override val transactionHash: String? = null,
    @SerialName("outgoing_payment_fees")
    val fees: CurrencyAmount? = null,
    @SerialName("outgoing_payment_payment_request_data")
    val paymentRequestData: PaymentRequestData? = null,
    @SerialName("outgoing_payment_failure_reason")
    val failureReason: PaymentFailureReason? = null,
    @SerialName("outgoing_payment_failure_message")
    val failureMessage: RichText? = null,
    @SerialName("outgoing_payment_payment_preimage")
    val paymentPreimage: String? = null,
) : LightningTransaction,
    Transaction,
    Entity {
    companion object {
        @JvmStatic
        fun getOutgoingPaymentQuery(id: String): Query<OutgoingPayment> = Query(
            queryPayload = """
query GetOutgoingPayment(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on OutgoingPayment {
            ...OutgoingPaymentFragment
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
fragment OutgoingPaymentFragment on OutgoingPayment {
    type: __typename
    outgoing_payment_id: id
    outgoing_payment_created_at: created_at
    outgoing_payment_updated_at: updated_at
    outgoing_payment_status: status
    outgoing_payment_resolved_at: resolved_at
    outgoing_payment_amount: amount {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    outgoing_payment_transaction_hash: transaction_hash
    outgoing_payment_fees: fees {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    outgoing_payment_payment_request_data: payment_request_data {
        type: __typename
        ... on InvoiceData {
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
            invoice_data_destination: destination {
                type: __typename
                graph_node_id: id
                graph_node_created_at: created_at
                graph_node_updated_at: updated_at
                graph_node_alias: alias
                graph_node_bitcoin_network: bitcoin_network
                graph_node_color: color
                graph_node_conductivity: conductivity
                graph_node_display_name: display_name
                graph_node_public_key: public_key
            }
        }
    }
    outgoing_payment_failure_reason: failure_reason
    outgoing_payment_failure_message: failure_message {
        type: __typename
        rich_text_text: text
    }
    outgoing_payment_payment_preimage: payment_preimage
}"""
    }
}
