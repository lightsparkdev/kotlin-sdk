// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("ScreenBitcoinAddressesOutput")
data class ScreenBitcoinAddressesOutput(

    @SerialName("screen_bitcoin_addresses_output_ratings")
    val ratings: List<RiskRating>,
) {

    companion object {

        const val FRAGMENT = """
fragment ScreenBitcoinAddressesOutputFragment on ScreenBitcoinAddressesOutput {
    type: __typename
    screen_bitcoin_addresses_output_ratings: ratings
}"""
    }
}
