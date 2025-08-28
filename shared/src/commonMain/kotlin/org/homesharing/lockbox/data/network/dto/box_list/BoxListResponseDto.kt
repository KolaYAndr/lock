package org.homesharing.lockbox.data.network.dto.box_list

import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusResponseDto

@Serializable
internal data class BoxListResponseDto(
    val boxes: List<BoxWithStatusResponseDto>,
    val total: Int,
    val page: Int,
    val size: Int,
)
