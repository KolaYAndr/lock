package org.homesharing.lockbox.data.network.dto.confirm_email

import kotlinx.serialization.Serializable

@Serializable
internal data class ConfirmEmailRequest(
    val mail: String
)
