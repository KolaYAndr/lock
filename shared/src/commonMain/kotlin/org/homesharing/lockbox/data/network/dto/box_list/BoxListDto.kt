package org.homesharing.lockbox.data.network.dto.box_list

import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusDto

@Serializable
internal data class BoxListDto(
    val boxes: List<BoxWithStatusDto>,
    val total: Int,
    val page: Int,
    val size: Int,
)
