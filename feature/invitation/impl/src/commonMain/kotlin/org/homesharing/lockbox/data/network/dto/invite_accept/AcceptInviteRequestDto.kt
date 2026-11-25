package org.homesharing.lockbox.data.network.dto.invite_accept

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AcceptInviteRequestDto(
    @SerialName("terms_version")
    val termsVersion: Long,
)
