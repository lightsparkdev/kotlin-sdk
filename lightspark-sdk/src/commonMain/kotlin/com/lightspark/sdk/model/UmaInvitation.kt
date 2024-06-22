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
 * This is an object representing an UMA.ME invitation.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param code The code that uniquely identifies this invitation.
 * @param url The URL where this invitation can be claimed.
 * @param inviterUma The UMA of the user who created the invitation.
 * @param incentivesStatus The current status of the incentives that may be tied to this invitation.
 * @param inviteeUma The UMA of the user who claimed the invitation.
 * @param incentivesIneligibilityReason The reason why the invitation is not eligible for incentives, if applicable.
 */
@Serializable
@SerialName("UmaInvitation")
data class UmaInvitation(
    @SerialName("uma_invitation_id")
    override val id: String,
    @SerialName("uma_invitation_created_at")
    override val createdAt: Instant,
    @SerialName("uma_invitation_updated_at")
    override val updatedAt: Instant,
    @SerialName("uma_invitation_code")
    val code: String,
    @SerialName("uma_invitation_url")
    val url: String,
    @SerialName("uma_invitation_inviter_uma")
    val inviterUma: String,
    @SerialName("uma_invitation_incentives_status")
    val incentivesStatus: IncentivesStatus,
    @SerialName("uma_invitation_invitee_uma")
    val inviteeUma: String? = null,
    @SerialName("uma_invitation_incentives_ineligibility_reason")
    val incentivesIneligibilityReason: IncentivesIneligibilityReason? = null,
) : Entity {
    companion object {
        @JvmStatic
        fun getUmaInvitationQuery(id: String): Query<UmaInvitation> = Query(
            queryPayload = """
query GetUmaInvitation(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on UmaInvitation {
            ...UmaInvitationFragment
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
fragment UmaInvitationFragment on UmaInvitation {
    type: __typename
    uma_invitation_id: id
    uma_invitation_created_at: created_at
    uma_invitation_updated_at: updated_at
    uma_invitation_code: code
    uma_invitation_url: url
    uma_invitation_inviter_uma: inviter_uma
    uma_invitation_invitee_uma: invitee_uma
    uma_invitation_incentives_status: incentives_status
    uma_invitation_incentives_ineligibility_reason: incentives_ineligibility_reason
}"""
    }
}
