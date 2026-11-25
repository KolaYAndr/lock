package org.homesharing.lockbox.data.network.dto.otp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OtpVerifyResponseDto(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("is_new_user")
    val isNewUser: Boolean,
)
