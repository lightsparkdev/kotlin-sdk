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
                umaEncryptionPubKeyHex = "04f76978c7ad636928dcba0202b8d602a7ba12921ccd1ca7f312ceac93cee747c0c5d5dbae9b3f841607ebc18ace0dfaa2cb87a0e976b3ac70f72102a3c0e8aeab"// System.getenv("LIGHTSPARK_UMA_ENCRYPTION_PUBKEY")
                    ?: error("LIGHTSPARK_UMA_ENCRYPTION_PUBKEY not set"),
                umaEncryptionPrivKeyHex = "97e004e98c59cc673ac0511cb1f8b249b1e5d1b20d98e6af0591800a6376805f" // System.getenv("LIGHTSPARK_UMA_ENCRYPTION_PRIVKEY")
                    ?: error("LIGHTSPARK_UMA_ENCRYPTION_PRIVKEY not set"),
                umaSigningPubKeyHex = "04f76978c7ad636928dcba0202b8d602a7ba12921ccd1ca7f312ceac93cee747c0c5d5dbae9b3f841607ebc18ace0dfaa2cb87a0e976b3ac70f72102a3c0e8aeab" //System.getenv("LIGHTSPARK_UMA_SIGNING_PUBKEY")
                    ?: error("LIGHTSPARK_UMA_SIGNING_PUBKEY not set"),
                umaSigningPrivKeyHex = "97e004e98c59cc673ac0511cb1f8b249b1e5d1b20d98e6af0591800a6376805f" // System.getenv("LIGHTSPARK_UMA_SIGNING_PRIVKEY")
                    ?: error("LIGHTSPARK_UMA_SIGNING_PRIVKEY not set"),
                clientBaseURL = System.getenv("LIGHTSPARK_EXAMPLE_BASE_URL"),
            )
        }
    }
}
