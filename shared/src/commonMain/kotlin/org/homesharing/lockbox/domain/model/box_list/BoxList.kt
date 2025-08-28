package org.homesharing.lockbox.domain.model.box_list

import org.homesharing.lockbox.domain.model.box.BoxWithStatus

data class BoxList(
    val boxes: List<BoxWithStatus>,
    val total: Int,
    val page: Int,
    val size: Int,
)
