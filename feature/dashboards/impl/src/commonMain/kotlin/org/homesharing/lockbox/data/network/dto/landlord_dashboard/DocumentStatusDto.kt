package org.homesharing.lockbox.data.network.dto.landlord_dashboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class DocumentStatusDto {
    @SerialName("pending")
    Pending,

    @SerialName("created")
    Created,

    @SerialName("failed")
    Failed,

    @SerialName("none")
    None,
}
