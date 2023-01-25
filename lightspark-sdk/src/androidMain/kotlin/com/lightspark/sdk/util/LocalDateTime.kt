package com.lightspark.sdk.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaInstant
import java.text.SimpleDateFormat
import java.util.*

actual fun LocalDateTime.format(format: String): String =
    SimpleDateFormat(format, Locale.getDefault(Locale.Category.FORMAT))
        .format(Date.from(toInstant(TimeZone.UTC).toJavaInstant()))