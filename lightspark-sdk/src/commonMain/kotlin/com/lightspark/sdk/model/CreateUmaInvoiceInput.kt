// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateUmaInvoiceInput")
data class CreateUmaInvoiceInput(
    val nodeId: String,
    val amountMsats: Long,
    val metadataHash: String,
    val expirySecs: Int? = null,
    val receiverHash: String? = null,
) {
    companion object {
    }
}
