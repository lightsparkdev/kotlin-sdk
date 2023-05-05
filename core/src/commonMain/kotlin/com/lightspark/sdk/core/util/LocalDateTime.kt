package com.lightspark.sdk.core.util

import kotlinx.datetime.LocalDateTime

expect fun LocalDateTime.format(format: String): String
