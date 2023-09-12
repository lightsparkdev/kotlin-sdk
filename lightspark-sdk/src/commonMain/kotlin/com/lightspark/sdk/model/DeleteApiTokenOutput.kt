// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("DeleteApiTokenOutput")
data class DeleteApiTokenOutput(
    @SerialName("delete_api_token_output_account")
    val accountId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment DeleteApiTokenOutputFragment on DeleteApiTokenOutput {
    type: __typename
    delete_api_token_output_account: account {
        id
    }
}"""
    }
}
