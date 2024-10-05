package com.straccion.appmotos1.domain.model

sealed class Response<out T> {
    data object Loading: Response<Nothing>()

    data class Success<out T>(val data: T): Response<T>()
    data class Failure<out T>(val exception: Exception?): Response<T>()
}