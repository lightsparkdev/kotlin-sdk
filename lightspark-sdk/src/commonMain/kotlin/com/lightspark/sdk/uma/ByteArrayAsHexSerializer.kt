package com.lightspark.sdk.uma

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@OptIn(ExperimentalStdlibApi::class)
class ByteArrayAsHexSerializer : KSerializer<ByteArray> {
    override val descriptor = ByteArraySerializer().descriptor

    override fun serialize(encoder: Encoder, value: ByteArray) {
        encoder.encodeString(value.toHexString())
    }

    override fun deserialize(decoder: Decoder): ByteArray {
        return decoder.decodeString().hexToByteArray()
    }
}
