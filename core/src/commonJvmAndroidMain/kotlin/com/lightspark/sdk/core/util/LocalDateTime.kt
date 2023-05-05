package com.lightspark.sdk.core.util

import java.text.SimpleDateFormat
import java.util.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaInstant

actual fun LocalDateTime.format(format: String): String =
    SimpleDateFormat(format, Locale.getDefault(Locale.Category.FORMAT))
        .format(Date.from(toInstant(TimeZone.UTC).toJavaInstant()))
