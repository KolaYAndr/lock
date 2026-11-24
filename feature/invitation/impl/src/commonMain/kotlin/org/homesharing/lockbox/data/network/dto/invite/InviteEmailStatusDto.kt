package org.homesharing.lockbox.data.network.dto.invite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class InviteEmailStatusDto {
    @SerialName("pending")
    Pending,

    @SerialName("sent")
    Sent,

    @SerialName("failed")
    Failed,
}
