package org.homesharing.lockbox.data.network.dto.flat_photo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ReorderPhotosRequestDto(
    @SerialName("photo_ids")
    val photoIds: List<String>,
)
