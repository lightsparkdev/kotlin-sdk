package com.lightspark.androiddemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LightsparkDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: LightsparkDemoApplication
            private set
    }
}