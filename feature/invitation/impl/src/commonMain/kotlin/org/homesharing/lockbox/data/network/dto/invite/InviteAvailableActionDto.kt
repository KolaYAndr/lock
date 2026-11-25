package org.homesharing.lockbox.data.network.dto.invite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class InviteAvailableActionDto {
    @SerialName("update")
    Update,

    @SerialName("resend")
    Resend,

    @SerialName("revoke")
    Revoke,
}
