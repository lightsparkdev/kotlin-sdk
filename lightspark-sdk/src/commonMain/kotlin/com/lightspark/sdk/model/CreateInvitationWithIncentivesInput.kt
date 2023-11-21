// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateInvitationWithIncentivesInput")
data class CreateInvitationWithIncentivesInput(
    val inviterUma: String,
    val inviterPhoneHash: String,
    val inviterRegion: RegionCode,
) {
    companion object {
    }
}
