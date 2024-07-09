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
 */
@Serializable
@SerialName("Signable")
data class Signable(
    @SerialName("signable_id")
    override val id: String,
    @SerialName("signable_created_at")
    override val createdAt: Instant,
    @SerialName("signable_updated_at")
    override val updatedAt: Instant,
) : Entity {
    companion object {
        @JvmStatic
        fun getSignableQuery(id: String): Query<Signable> = Query(
            queryPayload = """
query GetSignable(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on Signable {
            ...SignableFragment
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
fragment SignableFragment on Signable {
    type: __typename
    signable_id: id
    signable_created_at: created_at
    signable_updated_at: updated_at
}"""
    }
}
