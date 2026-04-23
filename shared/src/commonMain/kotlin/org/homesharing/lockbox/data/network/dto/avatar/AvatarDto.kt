package org.homesharing.lockbox.data.network.dto.avatar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AvatarDto(
    @SerialName("download_url")
    val downloadUrl: String
)