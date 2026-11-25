package org.homesharing.lockbox.data.network.api.response

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

sealed interface ApiResponse<out T> {
    @Serializable
    data class Success<T>(
        val data: T
    ) : ApiResponse<T>

    @OptIn(ExperimentalSerializationApi::class)
    @Serializable
    data class Failure(
        @SerialName("error_code")
        @JsonNames("errorCode")
        val errorCode: String,
        val message: String,
    ) : ApiResponse<Nothing>
}
