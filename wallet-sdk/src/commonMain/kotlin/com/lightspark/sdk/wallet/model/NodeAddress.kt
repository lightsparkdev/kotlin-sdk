// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An object that represents the address of a node on the Lightning Network.
 * @param address The string representation of the address.
 */
@Serializable
@SerialName("NodeAddress")
data class NodeAddress(

    @SerialName("node_address_address")
    val address: String,
) {

    companion object {

        const val FRAGMENT = """
fragment NodeAddressFragment on NodeAddress {
    type: __typename
    node_address_address: address
    node_address_type: type
}"""
    }
}
