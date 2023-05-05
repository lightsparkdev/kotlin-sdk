// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("Secret")
data class Secret(

    @SerialName("secret_encrypted_value")
    val encryptedValue: String,
    @SerialName("secret_cipher")
    val cipher: String,
) {

    companion object {

        const val FRAGMENT = """
fragment SecretFragment on Secret {
    type: __typename
    secret_encrypted_value: encrypted_value
    secret_cipher: cipher
}"""
    }
}
