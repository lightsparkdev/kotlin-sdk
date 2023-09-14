// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateBitcoinFundingAddressOutput")
data class CreateBitcoinFundingAddressOutput(
    @SerialName("create_bitcoin_funding_address_output_bitcoin_address")
    val bitcoinAddress: String,
) {
    companion object {
        const val FRAGMENT = """
fragment CreateBitcoinFundingAddressOutputFragment on CreateBitcoinFundingAddressOutput {
    type: __typename
    create_bitcoin_funding_address_output_bitcoin_address: bitcoin_address
}"""
    }
}
