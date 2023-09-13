package com.lightspark.sdk.crypto

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
    fun testEcies() {
        val keyPair = Secp256k1.generateKeyPair()
        val message = "hello world".toByteArray()
        val encrypted = Secp256k1.encryptEcies(message, keyPair.publicKey)
        val decrypted = Secp256k1.decryptEcies(encrypted, keyPair.privateKey)
        assertTrue(message.contentEquals(decrypted))
    }
}
