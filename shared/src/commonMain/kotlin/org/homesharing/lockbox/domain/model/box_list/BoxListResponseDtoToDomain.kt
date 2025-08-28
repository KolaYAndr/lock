package org.homesharing.lockbox.domain.model.box_list

import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.domain.model.box.toDomain

internal fun BoxListResponseDto.toDomain(): BoxList = BoxList(
    boxes = boxes.map { it.toDomain() },
    total = total,
    page = page,
    size = size
)
