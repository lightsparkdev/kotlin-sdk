// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param provider The compliance provider that is going to screen the node. You need to be a customer of the selected provider and store the API key on the Lightspark account setting page.
 * @param nodePubkey The public key of the lightning node that needs to be screened.
 */
@Serializable
@SerialName("ScreenNodeInput")
data class ScreenNodeInput(
    val provider: ComplianceProvider,
    val nodePubkey: String,
) {
    companion object {
    }
}
