@file:OptIn(ExperimentalStdlibApi::class)

package com.lightspark

data class RemoteSigningConfig(
    val apiClientID: String,
    val apiClientSecret: String,
    val webhookSecret: String,
    val masterSeedHex: String,
    val clientBaseURL: String?,
) {
    val masterSeed: ByteArray
        get() = masterSeedHex.hexToByteArray()

    companion object {
        fun fromEnv(): RemoteSigningConfig {
            return RemoteSigningConfig(
                apiClientID = System.getenv("LIGHTSPARK_API_TOKEN_CLIENT_ID")
                    ?: error("LIGHTSPARK_API_TOKEN_CLIENT_ID not set"),
                apiClientSecret = System.getenv("LIGHTSPARK_API_TOKEN_CLIENT_SECRET")
                    ?: error("LIGHTSPARK_API_TOKEN_CLIENT_SECRET not set"),
                webhookSecret = System.getenv("LIGHTSPARK_WEBHOOK_SECRET")
                    ?: error("LIGHTSPARK_WEBHOOK_SECRET not set"),
                masterSeedHex = System.getenv("LIGHTSPARK_MASTER_SEED_HEX")
                    ?: error("LIGHTSPARK_MASTER_SEED_HEX not set"),
                clientBaseURL = System.getenv("LIGHTSPARK_EXAMPLE_BASE_URL"),
            )
        }
    }
}
