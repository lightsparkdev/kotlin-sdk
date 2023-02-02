package com.lightspark.androiddemo.auth

import com.lightspark.androiddemo.LightsparkDemoApplication
import com.lightspark.sdk.auth.DataStoreAuthStateStorage

// This is a singleton that provides the AuthStateStorage instance. Note that in most real apps,
// this should probably be injected via a dependency injection framework instead.
object OAuthStoreProvider {
    val authStorageInstance by lazy {
        DataStoreAuthStateStorage(LightsparkDemoApplication.instance)
    }
}