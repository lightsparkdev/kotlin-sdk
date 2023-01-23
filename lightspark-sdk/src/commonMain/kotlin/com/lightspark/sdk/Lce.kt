package com.lightspark.sdk

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Lce<out T> {
    data class Content<T>(val data: T) : Lce<T>
    data class Error(val exception: Throwable? = null) : Lce<Nothing>
    object Loading : Lce<Nothing>
}

fun <T> Flow<T>.asLce(): Flow<Lce<T>> {
    return this
        .map<T, Lce<T>> {
            Lce.Content(it)
        }
        .onStart { emit(Lce.Loading) }
        .catch { emit(Lce.Error(it)) }
}