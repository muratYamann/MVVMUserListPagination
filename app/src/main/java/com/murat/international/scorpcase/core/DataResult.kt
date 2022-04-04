package com.murat.international.scorpcase.core

sealed class DataResult<out T> {
    data class Progress<T>(var isLoading: Boolean) : DataResult<T>()
    data class Success<T>(var data: T?) : DataResult<T>()
    data class Failure<T>(val statusCode: Int = -1, val exception: Throwable? = null) : DataResult<T>()
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}