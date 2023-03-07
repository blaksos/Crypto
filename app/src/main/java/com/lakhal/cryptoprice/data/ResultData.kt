package com.lakhal.cryptoprice.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface ResultData<out T> {
    data class Success<T>(val data: T) : ResultData<T>
    data class Error(val exception: Throwable? = null) : ResultData<Nothing>
    object Loading : ResultData<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<ResultData<T>> {
    return this

        .map<T, ResultData<T>> {
            ResultData.Success(it)
        }
        .onStart { emit(ResultData.Loading) }
        .catch { emit(ResultData.Error(it)) }
}