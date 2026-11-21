package org.homesharing.lockbox.domain.model.room

data class RoomInput(
    val type: RoomType,
    val name: String? = null,
    val position: Int? = null,
)
