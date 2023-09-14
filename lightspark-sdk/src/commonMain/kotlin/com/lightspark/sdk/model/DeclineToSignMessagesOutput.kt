// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("DeclineToSignMessagesOutput")
data class DeclineToSignMessagesOutput(
    @SerialName("decline_to_sign_messages_output_declined_payloads")
    val declinedPayloads: List<SignablePayload>,
) {
    companion object {
        const val FRAGMENT = """
fragment DeclineToSignMessagesOutputFragment on DeclineToSignMessagesOutput {
    type: __typename
    decline_to_sign_messages_output_declined_payloads: declined_payloads {
        id
    }
}"""
    }
}
