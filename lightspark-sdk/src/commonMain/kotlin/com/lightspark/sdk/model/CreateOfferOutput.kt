// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateOfferOutput")
data class CreateOfferOutput(
    @SerialName("create_offer_output_offer")
    val offerId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment CreateOfferOutputFragment on CreateOfferOutput {
    type: __typename
    create_offer_output_offer: offer {
        id
    }
}"""
    }
}
