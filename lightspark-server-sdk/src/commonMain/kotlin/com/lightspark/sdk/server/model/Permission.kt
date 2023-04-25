// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = PermissionSerializer::class)
enum class Permission(val rawValue: String) {

    ALL("ALL"),

    MAINNET_VIEW("MAINNET_VIEW"),

    MAINNET_TRANSACT("MAINNET_TRANSACT"),

    MAINNET_MANAGE("MAINNET_MANAGE"),

    TESTNET_VIEW("TESTNET_VIEW"),

    TESTNET_TRANSACT("TESTNET_TRANSACT"),

    TESTNET_MANAGE("TESTNET_MANAGE"),

    REGTEST_VIEW("REGTEST_VIEW"),

    REGTEST_TRANSACT("REGTEST_TRANSACT"),

    REGTEST_MANAGE("REGTEST_MANAGE"),

    USER_VIEW("USER_VIEW"),

    USER_MANAGE("USER_MANAGE"),

    ACCOUNT_VIEW("ACCOUNT_VIEW"),

    ACCOUNT_MANAGE("ACCOUNT_MANAGE"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object PermissionSerializer :
    EnumSerializer<Permission>(
        Permission::class,
        { rawValue ->
            Permission.values().firstOrNull { it.rawValue == rawValue } ?: Permission.FUTURE_VALUE
        },
    )
