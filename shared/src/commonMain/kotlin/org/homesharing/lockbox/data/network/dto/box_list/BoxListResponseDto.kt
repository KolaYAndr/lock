package org.homesharing.lockbox.data.network.dto.box_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto

@Serializable
data class BoxListResponseDto(
    val boxes: List<BoxResponseDto>,
    @SerialName("total_boxes")
    val totalBoxes: Int,
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("boxes_on_page")
    val boxesOnPage: Int,
)
