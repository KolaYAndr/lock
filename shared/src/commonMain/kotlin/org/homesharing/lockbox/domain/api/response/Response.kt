package org.homesharing.lockbox.domain.api.response

sealed interface Response<out T> {
    data class Failure(val message: String) : Response<Nothing>
    data class Success<T>(val data: T) : Response<T>
}
