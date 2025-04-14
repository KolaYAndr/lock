package org.homesharing.lockbox.data.network.dto.sign_up

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponseDto(val user: UserBody)

@Serializable
data class UserBody(
    val id: String,
    val username: String,
    val email: String,
    @SerialName("first_name")
    val firstName: String? = null,
    @SerialName("last_name")
    val lastName: String? = null,
    @SerialName("email_verified")
    val emailVerified: Boolean,
    val enabled: Boolean,
)
