package org.homesharing.lockbox.data.network.dto.sign_up

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponseDto(
    @SerialName("body")
    val body: User?,
    @SerialName("error")
    val error: Boolean,
    @SerialName("error_text")
    val errorText: String?
)

@Serializable
data class User(val userBody: UserBody)

@Serializable
data class UserBody(
    val id: String,
    val username: String,
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("email_verified")
    val emailVerified: Boolean,
    val enabled: Boolean
)
