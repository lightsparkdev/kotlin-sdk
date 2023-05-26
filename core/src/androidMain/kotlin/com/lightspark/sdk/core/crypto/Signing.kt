@file:JvmName("AndroidSigning")

package com.lightspark.sdk.core.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.Signature
import java.security.spec.MGF1ParameterSpec
import java.security.spec.PSSParameterSpec
import java.security.spec.RSAKeyGenParameterSpec

internal actual fun signUsingAlias(payload: ByteArray, keyAlias: String): ByteArray {
    val androidKeyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }
    val entry = androidKeyStore.getEntry(keyAlias, null)
    if (entry !is KeyStore.PrivateKeyEntry) {
        Log.w("Lightspark", "Keystore entry is not an instance of a PrivateKeyEntry")
        throw MissingKeyException(keyAlias)
    }
    val signature = try {
        Signature.getInstance("SHA256withRSA/PSS")
    } catch (e: NoSuchAlgorithmException) {
        // Fallback to RSASSA-PSS if SHA256withRSA/PSS is not supported
        Signature.getInstance("RSASSA-PSS").apply {
            setParameter(PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1))
        }
    }
    signature.initSign(entry.privateKey)
    signature.update(payload)
    return signature.sign()
}

fun androidKeystoreContainsPrivateKeyForAlias(keyAlias: String): Boolean {
    val androidKeyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }
    val entry = androidKeyStore.getEntry(keyAlias, null)
    return entry is KeyStore.PrivateKeyEntry
}

fun generateSigningKeyPairInAndroidKeyStore(keyAlias: String): KeyPair {
    val keyGen = try {
        KeyPairGenerator.getInstance("RSA", "AndroidKeyStore")
    } catch (e: Exception) {
        Log.w("Lightspark", "Failed to use AndroidKeyStore. Falling back to general key store.", e)
        return generateSigningKeyPair()
    }
    val spec = KeyGenParameterSpec.Builder(
        keyAlias,
        KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY,
    )
        .setAlgorithmParameterSpec(RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4))
        .build()
    keyGen.initialize(spec)
    return keyGen.generateKeyPair()
}
