package com.lightspark.sdk.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.reflect.KClass

open class EnumSerializer<T : Enum<T>>(
    enumClass: KClass<T>,
    private val fromString: (String) -> T,
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(enumClass.simpleName!!, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeString(value.name)
    }

    override fun deserialize(decoder: Decoder): T {
        return fromString(decoder.decodeString())
    }
}
