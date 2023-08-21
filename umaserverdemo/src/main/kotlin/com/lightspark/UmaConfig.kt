@file:OptIn(ExperimentalStdlibApi::class)

package com.lightspark

data class UmaConfig(
    val apiClientID: String,
    val apiClientSecret: String,
    val nodeID: String,
    val username: String,
    val userID: String,
    val umaEncryptionPubKeyHex: String,
    val umaEncryptionPrivKeyHex: String,
    val umaSigningPubKeyHex: String,
    val umaSigningPrivKeyHex: String,
    val clientBaseURL: String?,
) {
    val umaEncryptionPubKey: ByteArray
        get() = umaEncryptionPubKeyHex.hexToByteArray()

    val umaEncryptionPrivKey: ByteArray
        get() = umaEncryptionPrivKeyHex.hexToByteArray()

    val umaSigningPubKey: ByteArray
        get() = umaSigningPubKeyHex.hexToByteArray()

    val umaSigningPrivKey: ByteArray
        get() = umaSigningPrivKeyHex.hexToByteArray()

    companion object {
        fun fromEnv(): UmaConfig {
            return UmaConfig(
                apiClientID = System.getenv("LIGHTSPARK_API_TOKEN_CLIENT_ID")
                    ?: error("LIGHTSPARK_API_TOKEN_CLIENT_ID not set"),
                apiClientSecret = System.getenv("LIGHTSPARK_API_TOKEN_CLIENT_SECRET")
                    ?: error("LIGHTSPARK_API_TOKEN_CLIENT_SECRET not set"),
                nodeID = System.getenv("LIGHTSPARK_UMA_NODE_ID") ?: error("LIGHTSPARK_UMA_NODE_ID not set"),
                username = System.getenv("LIGHTSPARK_UMA_RECEIVER_USER")
                    ?: error("LIGHTSPARK_UMA_RECEIVER_USER not set"),
                // Static UUID so that callback URLs are always the same.
                userID = "4b41ae03-01b8-4974-8d26-26a35d28851b",
                umaEncryptionPubKeyHex = System.getenv("LIGHTSPARK_UMA_ENCRYPTION_PUBKEY")
                    ?: error("LIGHTSPARK_UMA_ENCRYPTION_PUBKEY not set"),
                umaEncryptionPrivKeyHex = System.getenv("LIGHTSPARK_UMA_ENCRYPTION_PRIVKEY")
                    ?: error("LIGHTSPARK_UMA_ENCRYPTION_PRIVKEY not set"),
                umaSigningPubKeyHex = System.getenv("LIGHTSPARK_UMA_SIGNING_PUBKEY")
                    ?: error("LIGHTSPARK_UMA_SIGNING_PUBKEY not set"),
                umaSigningPrivKeyHex = System.getenv("LIGHTSPARK_UMA_SIGNING_PRIVKEY")
                    ?: error("LIGHTSPARK_UMA_SIGNING_PRIVKEY not set"),
                clientBaseURL = System.getenv("LIGHTSPARK_EXAMPLE_BASE_URL"),
            )
        }
    }
}
