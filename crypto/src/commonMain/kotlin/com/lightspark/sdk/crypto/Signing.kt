package com.lightspark.sdk.crypto

import fr.acinq.bitcoin.DeterministicWallet
import fr.acinq.bitcoin.DeterministicWallet.derivePrivateKey
import fr.acinq.bitcoin.MnemonicCode
import fr.acinq.secp256k1.Secp256k1
import kotlin.jvm.JvmOverloads

object Signing {
    fun getMnemonicSeedPhrase(entropy: ByteArray): List<String> {
        return MnemonicCode.toMnemonics(entropy)
    }

    @JvmOverloads
    fun mnemonicToSeed(mnemonic: List<String>, passphrase: String = ""): ByteArray {
        return MnemonicCode.toSeed(mnemonic, passphrase)
    }

    fun ecdh(privateKey: ByteArray, otherPubKey: ByteArray): ByteArray {
        return Secp256k1.ecdh(privateKey, otherPubKey)
    }

    fun deriveKey(privateKeySeed: ByteArray, derivationPath: String): DeterministicWallet.ExtendedPrivateKey {
        val extendedPrivateKey = DeterministicWallet.generate(privateKeySeed)
        return derivePrivateKey(extendedPrivateKey, derivationPath)
    }

    @JvmOverloads
    fun signMessage(
        message: ByteArray,
        privateKeySeed: ByteArray,
        derivationPath: String? = null,
        multTweak: ByteArray? = null,
        addTweak: ByteArray? = null,
    ): ByteArray {
        val hasMultTweak = multTweak != null
        val hasAddTweak = addTweak != null
        if (hasAddTweak != hasMultTweak) {
            throw IllegalArgumentException("You must provide either both or neither of multTweak and an addTweak")
        }
        val extendedPrivateKey = DeterministicWallet.generate(privateKeySeed)
        val privateKey = if (derivationPath != null) {
            derivePrivateKey(extendedPrivateKey, derivationPath)
        } else {
            extendedPrivateKey
        }

        val tweakedKey = if (multTweak != null && addTweak != null) {
            val multiplied = Secp256k1.privKeyTweakMul(privateKey.privateKey.value.toByteArray(), multTweak)
            Secp256k1.privKeyTweakAdd(multiplied, addTweak)
        } else {
            privateKey.privateKey.value.toByteArray()
        }

        return Secp256k1.sign(message, tweakedKey)
    }
}
