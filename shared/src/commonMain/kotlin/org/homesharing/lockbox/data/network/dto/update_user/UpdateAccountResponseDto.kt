package org.homesharing.lockbox.data.network.dto.update_user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAccountResponseDto(
    val body: UpdateAccountBody,
    val error: Boolean,
    @SerialName("error_text")
    val errorText: String?
)

@Serializable
data class UpdateAccountBody(
    val email: String,
    @SerialName("email_verified")
    val emailVerified: Boolean,
    val enabled: Boolean,
    @SerialName("first_name")
    val firstName: String?,
    val id: String,
    @SerialName("last_name")
    val lastName: String?,
    val username: String
)
