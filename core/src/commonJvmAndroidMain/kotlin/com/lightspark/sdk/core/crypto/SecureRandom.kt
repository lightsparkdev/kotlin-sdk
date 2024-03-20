package com.lightspark.sdk.core.crypto

import java.security.SecureRandom

internal actual fun nextLong(): Long {
    return SecureRandom().nextLong()
}
