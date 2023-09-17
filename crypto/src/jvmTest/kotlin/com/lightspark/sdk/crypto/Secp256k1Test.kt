package com.lightspark.sdk.crypto

import com.lightspark.sdk.crypto.internal.Network
import com.lightspark.sdk.crypto.internal.hexToByteArray
import kotlin.test.Test
import kotlin.test.assertTrue

class Secp256k1Test {
    @Test
    fun testEcdsa() {
        val keyPair = Secp256k1.generateKeyPair()
        val message = "hello world".toByteArray()
        val signature = Secp256k1.signEcdsa(message, keyPair.privateKey)
        val verified = Secp256k1.verifyEcdsa(message, signature, keyPair.publicKey)
        assertTrue(verified)
    }

    @Test
    fun testEcdsaFromSeed() {
        val seedBytes = RemoteSigning.getMnemonicSeedPhrase(
            "0102030405060708091011121314151617181920212223242526272829303132".hexToByteArray()
        ).let {
            RemoteSigning.mnemonicToSeed(it)
        }
        val privateKeyHex = RemoteSigning.derivePrivateKey(seedBytes, Network.BITCOIN, "m/5")
        val publicKeyHex = RemoteSigning.derivePublicKeyHex(seedBytes, Network.BITCOIN, "m/5")
        val message = "hello world".toByteArray()
        val signature = Secp256k1.signEcdsa(message, privateKeyHex.hexToByteArray())
        val verified = Secp256k1.verifyEcdsa(message, signature, publicKeyHex.hexToByteArray())
        assertTrue(verified)
    }

    @Test
    fun testEcies() {
        val keyPair = Secp256k1.generateKeyPair()
        val message = "hello world".toByteArray()
        val encrypted = Secp256k1.encryptEcies(message, keyPair.publicKey)
        val decrypted = Secp256k1.decryptEcies(encrypted, keyPair.privateKey)
        assertTrue(message.contentEquals(decrypted))
    }
}
