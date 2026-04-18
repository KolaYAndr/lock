package org.homesharing.lockbox.domain.model.box_list

import org.homesharing.lockbox.data.network.dto.box_list.BoxListDto
import org.homesharing.lockbox.domain.model.box.toDomain

internal fun BoxListDto.toDomain() = BoxList(
    boxes = boxes.map { it.toDomain() },
    total = total,
    page = page,
    size = size
)
