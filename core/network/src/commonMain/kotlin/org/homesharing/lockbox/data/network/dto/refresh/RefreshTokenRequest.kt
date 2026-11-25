package org.homesharing.lockbox.data.network.dto.refresh

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RefreshTokenRequest(
    @SerialName("refresh_token")
    val refreshToken: String,
)
