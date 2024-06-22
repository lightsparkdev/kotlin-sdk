// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param multisigWalletAddressValidationParameters Vaildation parameters for the 2-of-2 multisig address. None if the address is not a 2-of-2 multisig address.
 */
@Serializable
@SerialName("CreateNodeWalletAddressOutput")
data class CreateNodeWalletAddressOutput(
    @SerialName("create_node_wallet_address_output_node")
    val nodeId: EntityId,
    @SerialName("create_node_wallet_address_output_wallet_address")
    val walletAddress: String,
    @SerialName("create_node_wallet_address_output_multisig_wallet_address_validation_parameters")
    val multisigWalletAddressValidationParameters: MultiSigAddressValidationParameters? = null,
) {
    companion object {
        const val FRAGMENT = """
fragment CreateNodeWalletAddressOutputFragment on CreateNodeWalletAddressOutput {
    type: __typename
    create_node_wallet_address_output_node: node {
        id
    }
    create_node_wallet_address_output_wallet_address: wallet_address
    create_node_wallet_address_output_multisig_wallet_address_validation_parameters: multisig_wallet_address_validation_parameters {
        type: __typename
        multi_sig_address_validation_parameters_counterparty_funding_pubkey: counterparty_funding_pubkey
        multi_sig_address_validation_parameters_funding_pubkey_derivation_path: funding_pubkey_derivation_path
    }
}"""
    }
}
