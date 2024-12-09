package org.homesharing.lockbox.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("body")
    val body: Body,
    @SerialName("error")
    val error: Boolean?,
    @SerialName("error_text")
    val errorText: String?
)