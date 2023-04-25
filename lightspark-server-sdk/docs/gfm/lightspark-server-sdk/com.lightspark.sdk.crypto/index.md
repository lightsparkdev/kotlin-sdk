//[lightspark-server-sdk](../../index.md)/[com.lightspark.sdk.crypto](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [MissingKeyException](-missing-key-exception/index.md) | [common]<br>class [MissingKeyException](-missing-key-exception/index.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [LightsparkException](../com.lightspark.sdk/-lightspark-exception/index.md) |
| [SigningKeyDecryptor](-signing-key-decryptor/index.md) | [common]<br>class [SigningKeyDecryptor](-signing-key-decryptor/index.md) |
| [UnsupportedCipherVersionException](-unsupported-cipher-version-exception/index.md) | [common]<br>class [UnsupportedCipherVersionException](-unsupported-cipher-version-exception/index.md)(version: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [LightsparkException](../com.lightspark.sdk/-lightspark-exception/index.md) |

## Functions

| Name | Summary |
|---|---|
| [pbkdf2](pbkdf2.md) | [common, commonJvmAndroid, ios]<br>[common]<br>expect fun [pbkdf2](pbkdf2.md)(password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), salt: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html), iterations: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), keyLengthBytes: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)<br>[commonJvmAndroid, ios]<br>actual fun [pbkdf2](pbkdf2.md)(password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), salt: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html), iterations: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), keyLengthBytes: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
