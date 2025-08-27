package org.homesharing.lockbox.data.network.api.response

import kotlinx.serialization.Serializable

internal sealed interface ApiResponse {
    @Serializable
    data class Success<T>(
        val data: T
    ) : ApiResponse

    @Serializable
    data class Failure(
        val errorCode: String,
        val message: String,
    ): ApiResponse
}