// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A connection between an account and the nodes it manages.
 * @param pageInfo An object that holds pagination information about the objects in this connection.
 * @param count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @param entities The nodes for the current page of this connection.
 * @param purpose The main purpose for the selected set of nodes. It is automatically determined from the nodes that are selected in this connection and is used for optimization purposes, as well as to determine the variation of the UI that should be presented to the user.
 */
@Serializable
@SerialName("AccountToNodesConnection")
data class AccountToNodesConnection(

    @SerialName("account_to_nodes_connection_page_info")
    val pageInfo: PageInfo,
    @SerialName("account_to_nodes_connection_count")
    val count: Int,
    @SerialName("account_to_nodes_connection_entities")
    val entities: List<LightsparkNode>,
    @SerialName("account_to_nodes_connection_purpose")
    val purpose: LightsparkNodePurpose? = null,
) {

    companion object {

        const val FRAGMENT = """
fragment AccountToNodesConnectionFragment on AccountToNodesConnection {
    type: __typename
    account_to_nodes_connection_page_info: page_info {
        type: __typename
        page_info_has_next_page: has_next_page
        page_info_has_previous_page: has_previous_page
        page_info_start_cursor: start_cursor
        page_info_end_cursor: end_cursor
    }
    account_to_nodes_connection_count: count
    account_to_nodes_connection_purpose: purpose
    account_to_nodes_connection_entities: entities {
        id
    }
}"""
    }
}
