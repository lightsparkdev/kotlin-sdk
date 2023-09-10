// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("ScreenNodeOutput")
data class ScreenNodeOutput(
    @SerialName("screen_node_output_rating")
    val rating: RiskRating,
) {
    companion object {
        const val FRAGMENT = """
fragment ScreenNodeOutputFragment on ScreenNodeOutput {
    type: __typename
    screen_node_output_rating: rating
}"""
    }
}
