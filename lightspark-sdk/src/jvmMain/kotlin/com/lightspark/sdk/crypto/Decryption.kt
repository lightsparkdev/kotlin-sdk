package com.lightspark.sdk.crypto

import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

internal actual fun decryptKeyCBC(key: ByteArray, iv: ByteArray, cipherText: ByteArray): ByteArray {
    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
    val secretKeySpec = SecretKeySpec(key, "AES")
    val ivParameterSpec = IvParameterSpec(iv)
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
    return cipher.doFinal(cipherText)
}

internal actual fun decryptKeyGCM(key: ByteArray, iv: ByteArray, cipherText: ByteArray): ByteArray {
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    val secretKeySpec = SecretKeySpec(key, "AES")
    val gcmParameterSpec = GCMParameterSpec(128, iv)
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec)
    return cipher.doFinal(cipherText)
}
