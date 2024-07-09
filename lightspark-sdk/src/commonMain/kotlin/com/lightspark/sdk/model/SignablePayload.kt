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
 *
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param payload The payload that needs to be signed.
 * @param derivationPath The consistent method for generating the same set of accounts and wallets for a given private key
 * @param status The status of the payload.
 * @param signableId The signable this payload belongs to.
 * @param addTweak The tweak value to add.
 * @param mulTweak The tweak value to multiply.
 */
@Serializable
@SerialName("SignablePayload")
data class SignablePayload(
    @SerialName("signable_payload_id")
    override val id: String,
    @SerialName("signable_payload_created_at")
    override val createdAt: Instant,
    @SerialName("signable_payload_updated_at")
    override val updatedAt: Instant,
    @SerialName("signable_payload_payload")
    val payload: String,
    @SerialName("signable_payload_derivation_path")
    val derivationPath: String,
    @SerialName("signable_payload_status")
    val status: SignablePayloadStatus,
    @SerialName("signable_payload_signable")
    val signableId: EntityId,
    @SerialName("signable_payload_add_tweak")
    val addTweak: String? = null,
    @SerialName("signable_payload_mul_tweak")
    val mulTweak: String? = null,
) : Entity {
    companion object {
        @JvmStatic
        fun getSignablePayloadQuery(id: String): Query<SignablePayload> = Query(
            queryPayload = """
query GetSignablePayload(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on SignablePayload {
            ...SignablePayloadFragment
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
fragment SignablePayloadFragment on SignablePayload {
    type: __typename
    signable_payload_id: id
    signable_payload_created_at: created_at
    signable_payload_updated_at: updated_at
    signable_payload_payload: payload
    signable_payload_derivation_path: derivation_path
    signable_payload_status: status
    signable_payload_add_tweak: add_tweak
    signable_payload_mul_tweak: mul_tweak
    signable_payload_signable: signable {
        id
    }
}"""
    }
}
