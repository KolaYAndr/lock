package org.homesharing.lockbox.data.network.dto.invite_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class EmailStatusDto {
    @SerialName("pending")
    Pending,

    @SerialName("sent")
    Sent,

    @SerialName("failed")
    Failed,
}
