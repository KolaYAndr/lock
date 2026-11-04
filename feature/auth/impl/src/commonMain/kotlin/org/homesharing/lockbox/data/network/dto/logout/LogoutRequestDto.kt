package org.homesharing.lockbox.data.network.dto.logout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LogoutRequestDto(
    @SerialName("refresh_token")
    val refreshToken: String,
)
