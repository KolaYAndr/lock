package org.homesharing.lockbox.data.network.dto.update_account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UpdateAccountDto(
    val email: String,
    @SerialName("email_verified")
    val emailVerified: Boolean,
    val enabled: Boolean,
    @SerialName("first_name")
    val firstName: String?,
    val id: String,
    @SerialName("last_name")
    val lastName: String?,
    val username: String,
)
