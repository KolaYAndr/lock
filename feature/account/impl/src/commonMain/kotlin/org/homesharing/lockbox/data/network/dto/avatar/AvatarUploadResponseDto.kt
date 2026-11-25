package org.homesharing.lockbox.data.network.dto.avatar

import kotlinx.serialization.Serializable

@Serializable
internal data class AvatarUploadResponseDto(
    val message: String,
)
