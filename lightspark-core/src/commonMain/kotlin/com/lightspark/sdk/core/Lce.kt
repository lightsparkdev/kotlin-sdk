package com.lightspark.sdk.core

import kotlinx.coroutines.flow.*

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

/**
 * A convenience function which wraps a query in a [Flow] that emits [Lce] states for loading, success, and error conditions.
 *
 * For example:
 * ```kotlin
 * val lceDashboard = wrapFlowableResult { lightsparkClient.getFullAccountDashboard() }
 * ```
 * @query A suspend function which returns the data to be wrapped in [Lce]
 * @return A [Flow] which emits [Lce] states for loading, success, and error conditions for the given query.
 */
fun <T> wrapWithLceFlow(query: suspend () -> T?): Flow<Lce<T>> = flow {
    val data = query() ?: throw Exception("No result data")
    emit(data)
}.asLce()
