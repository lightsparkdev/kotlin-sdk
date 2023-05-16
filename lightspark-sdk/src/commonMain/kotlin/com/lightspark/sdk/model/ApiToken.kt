// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

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
 * @param clientId An opaque identifier that should be used as a client_id (or username) in the HTTP Basic Authentication scheme when issuing requests against the Lightspark API.
 * @param name An arbitrary name chosen by the creator of the token to help identify the token in the list of tokens that have been created for the account.
 * @param permissions A list of permissions granted to the token.
 */
@Serializable
@SerialName("ApiToken")
data class ApiToken(

    @SerialName("api_token_id")
    override val id: String,
    @SerialName("api_token_created_at")
    override val createdAt: Instant,
    @SerialName("api_token_updated_at")
    override val updatedAt: Instant,
    @SerialName("api_token_client_id")
    val clientId: String,
    @SerialName("api_token_name")
    val name: String,
    @SerialName("api_token_permissions")
    val permissions: List<Permission>,
) : Entity {

    companion object {
        @JvmStatic
        fun getApiTokenQuery(id: String): Query<ApiToken> {
            return Query(
                queryPayload = """
query GetApiToken(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on ApiToken {
            ...ApiTokenFragment
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
fragment ApiTokenFragment on ApiToken {
    type: __typename
    api_token_id: id
    api_token_created_at: created_at
    api_token_updated_at: updated_at
    api_token_client_id: client_id
    api_token_name: name
    api_token_permissions: permissions
}"""
    }
}
