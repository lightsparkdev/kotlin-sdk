package com.lightspark.androiddemo

import android.app.Application

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