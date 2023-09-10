// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateNodeWalletAddressOutput")
data class CreateNodeWalletAddressOutput(
    @SerialName("create_node_wallet_address_output_node")
    val nodeId: EntityId,
    @SerialName("create_node_wallet_address_output_wallet_address")
    val walletAddress: String,
) {
    companion object {
        const val FRAGMENT = """
fragment CreateNodeWalletAddressOutputFragment on CreateNodeWalletAddressOutput {
    type: __typename
    create_node_wallet_address_output_node: node {
        id
    }
    create_node_wallet_address_output_wallet_address: wallet_address
}"""
    }
}
