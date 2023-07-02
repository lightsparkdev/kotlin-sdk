// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("PageInfo")
data class PageInfo(

    @SerialName("page_info_has_next_page")
    val hasNextPage: Boolean? = null,
    @SerialName("page_info_has_previous_page")
    val hasPreviousPage: Boolean? = null,
    @SerialName("page_info_start_cursor")
    val startCursor: String? = null,
    @SerialName("page_info_end_cursor")
    val endCursor: String? = null,
) {

    companion object {

        const val FRAGMENT = """
fragment PageInfoFragment on PageInfo {
    type: __typename
    page_info_has_next_page: has_next_page
    page_info_has_previous_page: has_previous_page
    page_info_start_cursor: start_cursor
    page_info_end_cursor: end_cursor
}"""
    }
}
