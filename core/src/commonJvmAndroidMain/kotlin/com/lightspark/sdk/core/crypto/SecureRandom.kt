package com.lightspark.sdk.core.crypto

import java.security.SecureRandom

internal actual fun nextInt(): Int {
    return SecureRandom().nextInt()
}
