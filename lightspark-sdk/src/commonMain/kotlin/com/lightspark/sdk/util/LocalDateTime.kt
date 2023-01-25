package com.lightspark.sdk.util

import kotlinx.datetime.LocalDateTime

expect fun LocalDateTime.format(format: String): String
