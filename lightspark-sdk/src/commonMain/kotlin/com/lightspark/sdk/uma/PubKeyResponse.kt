package com.lightspark.sdk.uma

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Response from another VASP when requesting public keys.
 *
 * @property signingPubKey The public key used to verify signatures from the VASP.
 * @property encryptionPubKey The public key used to encrypt TR info sent to the VASP.
 * @property expirationTimestamp Seconds since epoch at which these pub keys must be refreshed.
 *     They can be safely cached until this expiration (or forever if null).
 */
@Serializable
data class PubKeyResponse @JvmOverloads constructor(
    @Serializable(with = ByteArrayAsHexSerializer::class)
    val signingPubKey: ByteArray,
    @Serializable(with = ByteArrayAsHexSerializer::class)
    val encryptionPubKey: ByteArray,
    val expirationTimestamp: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PubKeyResponse

        if (!signingPubKey.contentEquals(other.signingPubKey)) return false
        if (!encryptionPubKey.contentEquals(other.encryptionPubKey)) return false
        if (expirationTimestamp != other.expirationTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = signingPubKey.contentHashCode()
        result = 31 * result + encryptionPubKey.contentHashCode()
        result = 31 * result + (expirationTimestamp?.hashCode() ?: 0)
        return result
    }

    fun toJson() = Json.encodeToString(this)
}
