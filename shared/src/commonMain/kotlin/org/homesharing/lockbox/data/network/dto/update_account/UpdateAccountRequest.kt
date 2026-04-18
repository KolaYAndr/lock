package org.homesharing.lockbox.data.network.dto.update_account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UpdateAccountRequest(
    val email: String? = null,
    val enabled: Boolean? = null,
    @SerialName("first_name")
    val firstName: String? = null,
    @SerialName("last_name")
    val lastName: String? = null,
)
