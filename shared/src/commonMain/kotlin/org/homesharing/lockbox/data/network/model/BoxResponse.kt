package org.homesharing.lockbox.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class BoxResponse {
    abstract val error: Boolean

    @SerialName("error_text")
    abstract val errorText: String?

    @Serializable
    data class Success(
        val alias: String?,
        @SerialName("mac_address")
        val macAddress: String,
        override val error: Boolean,
        @SerialName("error_text")
        override val errorText: String?
    ) : BoxResponse()

    @Serializable
    data class Error(
        val body: String?,
        override val error: Boolean,
        @SerialName("error_text")
        override val errorText: String?
    ): BoxResponse()
}