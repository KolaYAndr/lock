package org.homesharing.lockbox.data.network.dto.sign_up

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class SignUpResponseDto(val body: UserBody)

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
    val enabled: Boolean,
)
