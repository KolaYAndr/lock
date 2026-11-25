package org.homesharing.lockbox.data.network.dto.invite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class InviteStatusDto {
    @SerialName("sent")
    Sent,

    @SerialName("opened")
    Opened,

    @SerialName("accepted")
    Accepted,

    @SerialName("declined")
    Declined,

    @SerialName("revoked")
    Revoked,

    @SerialName("expired")
    Expired,
}
