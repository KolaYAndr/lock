package org.homesharing.lockbox.data.network.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val body: T? = null,
    val error: Boolean,
    @SerialName("error_text")
    val errorText: String? = null
)