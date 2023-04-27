// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.wallet.util.serializerFormat
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.jvm.JvmStatic

/**
 *
 * @property id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @property createdAt The date and time when this transaction was initiated.
 * @property updatedAt The date and time when the entity was last updated.
 * @property status The current status of this transaction.
 * @property amount The amount of money involved in this transaction.
 * @property resolvedAt The date and time when this transaction was completed or failed.
 * @property transactionHash The hash of this transaction, so it can be uniquely identified on the Lightning Network.
 */
interface LightningTransaction : Transaction, Entity {

    @SerialName("lightning_transaction_id")
    override val id: String

    @SerialName("lightning_transaction_created_at")
    override val createdAt: Instant

    @SerialName("lightning_transaction_updated_at")
    override val updatedAt: Instant

    @SerialName("lightning_transaction_status")
    override val status: TransactionStatus

    @SerialName("lightning_transaction_amount")
    override val amount: CurrencyAmount

    @SerialName("lightning_transaction_resolved_at")
    override val resolvedAt: Instant?

    @SerialName("lightning_transaction_transaction_hash")
    override val transactionHash: String?

    companion object {
        @JvmStatic
        fun getLightningTransactionQuery(id: String): Query<LightningTransaction> {
            return Query(
                queryPayload = """
query GetLightningTransaction(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on LightningTransaction {
            ...LightningTransactionFragment
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
fragment LightningTransactionFragment on LightningTransaction {
    type: __typename
    ... on IncomingPayment {
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
        incoming_payment_payment_request: payment_request {
            id
        }
    }
    ... on OutgoingPayment {
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
            }
        }
        outgoing_payment_failure_reason: failure_reason
        outgoing_payment_failure_message: failure_message {
            type: __typename
            rich_text_text: text
        }
    }
}"""
    }
}
