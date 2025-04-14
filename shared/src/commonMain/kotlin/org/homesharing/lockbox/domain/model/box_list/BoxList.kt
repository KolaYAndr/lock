package org.homesharing.lockbox.domain.model.box_list

import org.homesharing.lockbox.domain.model.box.Box

data class BoxList(
    val boxes: List<Box>,
    val totalBoxes: Int,
    val currentPage: Int,
    val boxesOnPage: Int,
)
