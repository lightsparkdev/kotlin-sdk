package com.lightspark.sdk.core.util

import platform.UIKit.UIDevice

class IosPlatform : Platform {
    override val platformName: String = UIDevice.currentDevice.systemName()
    override val version: String = UIDevice.currentDevice.systemVersion
    override fun getEnv(name: String): String? {
        return platform.posix.getenv(name)?.toString()
    }
}

actual fun getPlatform(): Platform = IosPlatform()
