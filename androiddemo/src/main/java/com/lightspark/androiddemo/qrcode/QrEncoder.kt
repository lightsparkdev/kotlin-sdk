package com.lightspark.androiddemo.qrcode

import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.ByteMatrix
import com.google.zxing.qrcode.encoder.Encoder

/**
 * Encodes a string into a QR code ByteMatrix. This is just a small wrapper around the ZXing library with sane defaults.
 */
class QrEncoder {
    operator fun invoke(qrData: String): ByteMatrix? {
        return Encoder.encode(
            qrData,
            ErrorCorrectionLevel.H,
            mapOf(
                EncodeHintType.CHARACTER_SET to "UTF-8",
                EncodeHintType.MARGIN to 16,
                EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H,
            )
        ).matrix
    }
}