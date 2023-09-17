@file:OptIn(ExperimentalUnsignedTypes::class)

package com.lightspark.sdk.crypto

import com.lightspark.sdk.crypto.internal.LightsparkSigner
import com.lightspark.sdk.crypto.internal.Mnemonic
import com.lightspark.sdk.crypto.internal.Network
import com.lightspark.sdk.crypto.internal.Seed
import com.lightspark.sdk.crypto.internal.hexToByteArray
import com.lightspark.sdk.crypto.internal.toByteArray
import com.lightspark.sdk.crypto.internal.use

object RemoteSigning {
    fun getMnemonicSeedPhrase(entropy: ByteArray): List<String> =
        Mnemonic.fromEntropy(entropy.map { it.toUByte() }).use {
            it.asString().split(" ")
        }

    fun mnemonicToSeed(mnemonic: List<String>): ByteArray =
        Mnemonic.fromPhrase(mnemonic.joinToString(" ")).use {
            Seed.fromMnemonic(it).use { seed ->
                seed.asBytes().toByteArray()
            }
        }

    fun ecdh(seedBytes: ByteArray, network: Network, otherPubKeyHex: String): ByteArray {
        return withSigner(seedBytes, network) { signer ->
            val otherPubKeyBytes = otherPubKeyHex.hexToByteArray().toUByteArray().toList()
            signer.ecdh(otherPubKeyBytes).toByteArray()
        }
    }

    fun derivePublicKey(seedBytes: ByteArray, network: Network, derivationPath: String): String {
        return withSigner(seedBytes, network) { signer ->
            signer.derivePublicKey(derivationPath)
        }
    }

    fun derivePrivateKey(seedBytes: ByteArray, network: Network, derivationPath: String): String {
        return withSigner(seedBytes, network) { signer ->
            signer.derivePrivateKey(derivationPath)
        }
    }

    @JvmOverloads
    fun signMessage(
        message: ByteArray,
        seedBytes: ByteArray,
        network: Network,
        derivationPath: String? = null,
        isRaw: Boolean = false,
        multTweak: ByteArray? = null,
        addTweak: ByteArray? = null,
    ): ByteArray {
        return withSigner(seedBytes, network) { signer ->
            signer.deriveKeyAndSign(
                message = message.toUByteArray().toList(),
                derivationPath = derivationPath ?: "",
                isRaw = isRaw,
                mulTweak = multTweak?.toUByteArray()?.toList(),
                addTweak = addTweak?.toUByteArray()?.toList(),
            ).toByteArray()
        }
    }

    fun getPerCommitmentPoint(
        seedBytes: ByteArray,
        network: Network,
        derivationPath: String,
        perCommitmentPointIndex: Long,
    ): ByteArray {
        return withSigner(seedBytes, network) { signer ->
            signer.getPerCommitmentPoint(derivationPath, perCommitmentPointIndex.toULong()).toByteArray()
        }
    }

    fun releasePerCommitmentSecret(
        seedBytes: ByteArray,
        network: Network,
        derivationPath: String,
        perCommitmentPointIndex: Long,
    ): ByteArray {
        return withSigner(seedBytes, network) { signer ->
            signer.releasePerCommitmentSecret(derivationPath, perCommitmentPointIndex.toULong()).toByteArray()
        }
    }

    fun generatePreimageNonce(
        seedBytes: ByteArray,
        network: Network
    ): ByteArray {
        return withSigner(seedBytes, network) { signer ->
            signer.generatePreimageNonce().toByteArray()
        }
    }

    fun generatePreimage(
        seedBytes: ByteArray,
        network: Network,
        preimageNonce: ByteArray,
    ): ByteArray {
        return withSigner(seedBytes, network) { signer ->
            signer.generatePreimage(preimageNonce.toUByteArray().toList()).toByteArray()
        }
    }

    fun generatePreimageHash(
        seedBytes: ByteArray,
        network: Network,
        preimageNonce: ByteArray,
    ): ByteArray {
        return withSigner(seedBytes, network) { signer ->
            signer.generatePreimageHash(preimageNonce.toUByteArray().toList()).toByteArray()
        }
    }

    fun signInvoice(
        seedBytes: ByteArray,
        network: Network,
        unsignedInvoice: String,
    ): InvoiceSignature {
        return withSigner(seedBytes, network) { signer ->
            signer.signInvoice(unsignedInvoice).use { signature ->
                InvoiceSignature(signature.getRecoveryId(), signature.getSignature().toByteArray())
            }
        }
    }

    fun signInvoiceHash(
        seedBytes: ByteArray,
        network: Network,
        unsignedInvoiceHash: ByteArray,
    ): InvoiceSignature {
        return withSigner(seedBytes, network) { signer ->
            signer.signInvoiceHash(unsignedInvoiceHash.toUByteArray().toList()).use { signature ->
                InvoiceSignature(signature.getRecoveryId(), signature.getSignature().toByteArray())
            }
        }
    }

    private fun <T> withSigner(seedBytes: ByteArray, network: Network, block: (LightsparkSigner) -> T): T {
        var signer: LightsparkSigner? = null
        try {
            signer = LightsparkSigner.fromBytes(seedBytes.toUByteArray().toList(), network)
            return block(signer)
        } finally {
            signer?.close()
        }
    }
}

data class InvoiceSignature(
    val recoveryId: Int,
    val signature: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as com.lightspark.sdk.crypto.InvoiceSignature

        if (recoveryId != other.recoveryId) return false
        if (!signature.contentEquals(other.signature)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = recoveryId
        result = 31 * result + signature.contentHashCode()
        return result
    }
}
