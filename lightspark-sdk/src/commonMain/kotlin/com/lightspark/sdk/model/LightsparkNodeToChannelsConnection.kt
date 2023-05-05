// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param pageInfo An object that holds pagination information about the objects in this connection.
 * @param count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @param entities The channels for the current page of this connection.
 */
@Serializable
@SerialName("LightsparkNodeToChannelsConnection")
data class LightsparkNodeToChannelsConnection(

    @SerialName("lightspark_node_to_channels_connection_page_info")
    val pageInfo: PageInfo,
    @SerialName("lightspark_node_to_channels_connection_count")
    val count: Int,
    @SerialName("lightspark_node_to_channels_connection_entities")
    val entities: List<Channel>,
) {

    companion object {

        const val FRAGMENT = """
fragment LightsparkNodeToChannelsConnectionFragment on LightsparkNodeToChannelsConnection {
    type: __typename
    lightspark_node_to_channels_connection_page_info: page_info {
        type: __typename
        page_info_has_next_page: has_next_page
        page_info_has_previous_page: has_previous_page
        page_info_start_cursor: start_cursor
        page_info_end_cursor: end_cursor
    }
    lightspark_node_to_channels_connection_count: count
    lightspark_node_to_channels_connection_entities: entities {
        id
    }
}"""
    }
}
