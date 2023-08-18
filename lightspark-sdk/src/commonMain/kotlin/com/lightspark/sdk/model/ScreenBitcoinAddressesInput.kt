// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("ScreenBitcoinAddressesInput")
data class ScreenBitcoinAddressesInput(

    val provider: CryptoSanctionsScreeningProvider,

    val addresses: List<String>,
) {

    companion object {
    }
}
