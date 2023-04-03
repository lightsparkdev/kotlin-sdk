// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param entities The channels for the current page of this connection.
 */
@Serializable
@SerialName("AccountToChannelsConnection")
data class AccountToChannelsConnection(

    @SerialName("account_to_channels_connection_count")
    val count: Int,
    @SerialName("account_to_channels_connection_entities")
    val entities: List<Channel>,
) {

    companion object {
        const val FRAGMENT = """
fragment AccountToChannelsConnectionFragment on AccountToChannelsConnection {
    type: __typename
    account_to_channels_connection_count: count
    account_to_channels_connection_entities: entities {
        id
    }
}"""
    }
}
