package com.yerayyas.pagingmarvelapi.utils

sealed class AsyncResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : AsyncResult<T>()
    data class Error(val exception: Exception) : AsyncResult<Nothing>()
    object Loading : AsyncResult<Nothing>()
}

