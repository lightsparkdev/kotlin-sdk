// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * Audit log actor who called the GraphQL mutation
 * @property id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @property createdAt The date and time when the entity was first created.
 * @property updatedAt The date and time when the entity was last updated.
 */
interface AuditLogActor : Entity {
    @SerialName("audit_log_actor_id")
    override val id: String

    @SerialName("audit_log_actor_created_at")
    override val createdAt: Instant

    @SerialName("audit_log_actor_updated_at")
    override val updatedAt: Instant

    companion object {
        @JvmStatic
        fun getAuditLogActorQuery(id: String): Query<AuditLogActor> = Query(
            queryPayload = """
query GetAuditLogActor(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on AuditLogActor {
            ...AuditLogActorFragment
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
fragment AuditLogActorFragment on AuditLogActor {
    type: __typename
    ... on ApiToken {
        type: __typename
        api_token_id: id
        api_token_created_at: created_at
        api_token_updated_at: updated_at
        api_token_client_id: client_id
        api_token_name: name
        api_token_permissions: permissions
        api_token_is_deleted: is_deleted
    }
}"""
    }
}
