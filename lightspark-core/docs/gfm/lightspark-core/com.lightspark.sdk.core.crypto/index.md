//[lightspark-core](../../index.md)/[com.lightspark.sdk.core.crypto](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [MissingKeyException](-missing-key-exception/index.md) | [common]<br>class [MissingKeyException](-missing-key-exception/index.md)(nodeId: String) : [LightsparkException](../com.lightspark.sdk.core/-lightspark-exception/index.md) |
| [NodeKeyCache](-node-key-cache/index.md) | [common]<br>class [NodeKeyCache](-node-key-cache/index.md) |
| [SigningKeyDecryptor](-signing-key-decryptor/index.md) | [common]<br>class [SigningKeyDecryptor](-signing-key-decryptor/index.md) |
| [UnsupportedCipherVersionException](-unsupported-cipher-version-exception/index.md) | [common]<br>class [UnsupportedCipherVersionException](-unsupported-cipher-version-exception/index.md)(version: Int) : [LightsparkException](../com.lightspark.sdk.core/-lightspark-exception/index.md) |

## Functions

| Name | Summary |
|---|---|
| [pbkdf2](pbkdf2.md) | [common, commonJvmAndroid, ios]<br>[common]<br>expect fun [pbkdf2](pbkdf2.md)(password: String, salt: ByteArray, iterations: Int, keyLengthBytes: Int): ByteArray<br>[commonJvmAndroid, ios]<br>actual fun [pbkdf2](pbkdf2.md)(password: String, salt: ByteArray, iterations: Int, keyLengthBytes: Int): ByteArray |
