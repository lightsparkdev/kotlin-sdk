// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("UpdateNodeSharedSecretOutput")
data class UpdateNodeSharedSecretOutput(
    @SerialName("update_node_shared_secret_output_node")
    val nodeId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment UpdateNodeSharedSecretOutputFragment on UpdateNodeSharedSecretOutput {
    type: __typename
    update_node_shared_secret_output_node: node {
        id
    }
}"""
    }
}
