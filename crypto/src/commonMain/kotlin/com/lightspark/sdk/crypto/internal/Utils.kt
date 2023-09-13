package com.lightspark.sdk.crypto.internal

internal fun String.hexToByteArray(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }

    val byteIterator = chunkedSequence(2)
        .map { it.toInt(16).toByte() }
        .iterator()

    return ByteArray(length / 2) { byteIterator.next() }
}

internal fun List<UByte>.toByteArray(): ByteArray = toUByteArray().toByteArray()
