// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("SignMessagesOutput")
data class SignMessagesOutput(
    @SerialName("sign_messages_output_signed_payloads")
    val signedPayloads: List<SignablePayload>,
) {
    companion object {
        const val FRAGMENT = """
fragment SignMessagesOutputFragment on SignMessagesOutput {
    type: __typename
    sign_messages_output_signed_payloads: signed_payloads {
        id
    }
}"""
    }
}
