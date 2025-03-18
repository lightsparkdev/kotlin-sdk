// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param nodeId The node from which to create the offer.
 * @param amountMsats The amount for which the offer should be created, in millisatoshis. Setting the amount to 0 will allow the payer to specify an amount.
 * @param description A short description of the offer.
 */
@Serializable
@SerialName("CreateOfferInput")
data class CreateOfferInput(
    val nodeId: String,
    val amountMsats: Long? = null,
    val description: String? = null,
) {
    companion object {
    }
}
