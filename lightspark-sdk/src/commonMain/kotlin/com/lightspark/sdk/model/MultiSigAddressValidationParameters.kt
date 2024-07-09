// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param counterpartyFundingPubkey The counterparty funding public key used to create the 2-of-2 multisig for the address.
 * @param fundingPubkeyDerivationPath The derivation path used to derive the funding public key for the 2-of-2 multisig address.
 */
@Serializable
@SerialName("MultiSigAddressValidationParameters")
data class MultiSigAddressValidationParameters(
    @SerialName("multi_sig_address_validation_parameters_counterparty_funding_pubkey")
    val counterpartyFundingPubkey: String,
    @SerialName("multi_sig_address_validation_parameters_funding_pubkey_derivation_path")
    val fundingPubkeyDerivationPath: String,
) {
    companion object {
        const val FRAGMENT = """
fragment MultiSigAddressValidationParametersFragment on MultiSigAddressValidationParameters {
    type: __typename
    multi_sig_address_validation_parameters_counterparty_funding_pubkey: counterparty_funding_pubkey
    multi_sig_address_validation_parameters_funding_pubkey_derivation_path: funding_pubkey_derivation_path
}"""
    }
}
