package org.homesharing.lockbox.data.network.api.response

import kotlinx.serialization.Serializable

sealed interface ApiResponse<out T> {
    @Serializable
    data class Success<T>(
        val data: T
    ) : ApiResponse<T>

    @Serializable
    data class Failure(
        val errorCode: String,
        val message: String,
    ) : ApiResponse<Nothing>
}
