package org.homesharing.lockbox.data.network.dto.room

import kotlinx.serialization.Serializable

@Serializable
internal data class RoomDto(
    val name: String,
    val position: Int?,
)
