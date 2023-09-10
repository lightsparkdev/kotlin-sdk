// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("FundNodeInput")
data class FundNodeInput(
    val nodeId: String,
    val amountSats: Long? = null,
) {
    companion object {
    }
}
