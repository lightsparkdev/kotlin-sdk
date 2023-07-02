// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("RichText")
data class RichText(

    @SerialName("rich_text_text")
    val text: String,
) {

    companion object {

        const val FRAGMENT = """
fragment RichTextFragment on RichText {
    type: __typename
    rich_text_text: text
}"""
    }
}
