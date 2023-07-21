// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param name An arbitrary name that the user can choose to identify the API token in a list.
 * @param permissions List of permissions to grant to the API token
 */
@Serializable
@SerialName("CreateApiTokenInput")
data class CreateApiTokenInput(

    val name: String,

    val permissions: List<Permission>,
) {

    companion object {
    }
}
