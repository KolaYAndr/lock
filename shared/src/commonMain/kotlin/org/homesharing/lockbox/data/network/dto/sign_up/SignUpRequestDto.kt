package org.homesharing.lockbox.data.network.dto.sign_up

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpRequestDto(
    @SerialName("password")
    val password: String,
    @SerialName("password_repeat")
    val repeatPassword: String,
    @SerialName("mail")
    val email: String,
)
