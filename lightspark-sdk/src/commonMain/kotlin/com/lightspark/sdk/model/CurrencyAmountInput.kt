// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CurrencyAmountInput")
data class CurrencyAmountInput(
    val value: Long,
    val unit: CurrencyUnit,
) {
    companion object {
    }
}
