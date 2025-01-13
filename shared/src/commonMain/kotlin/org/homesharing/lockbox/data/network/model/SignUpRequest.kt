package org.homesharing.lockbox.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("username")
    val userName: String,
    @SerialName("password")
    val password: String,
    @SerialName("password_repeat")
    val repeatPassword: String,
    @SerialName("mail")
    val email: String
)
