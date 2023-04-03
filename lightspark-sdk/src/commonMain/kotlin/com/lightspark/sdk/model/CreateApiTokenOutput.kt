// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param apiToken The API Token that has been created.
 * @param clientSecret The secret that should be used to authenticate against our API.
This secret is not stored and will never be available again after this. You must keep this secret secure as it grants access to your account.
 */
@Serializable
@SerialName("CreateApiTokenOutput")
data class CreateApiTokenOutput(

    @SerialName("create_api_token_output_api_token")
    val apiToken: ApiToken,
    @SerialName("create_api_token_output_client_secret")
    val clientSecret: String,
) {

    companion object {
        const val FRAGMENT = """
fragment CreateApiTokenOutputFragment on CreateApiTokenOutput {
    type: __typename
    create_api_token_output_api_token: api_token {
        type: __typename
        api_token_id: id
        api_token_created_at: created_at
        api_token_updated_at: updated_at
        api_token_client_id: client_id
        api_token_name: name
        api_token_permissions: permissions
    }
    create_api_token_output_client_secret: client_secret
}"""
    }
}
