package org.homesharing.lockbox.data.network.dto.room

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class RoomTypeDto {
    @SerialName("kitchen")
    Kitchen,

    @SerialName("bedroom")
    Bedroom,

    @SerialName("living_room")
    LivingRoom,

    @SerialName("shower_room")
    ShowerRoom,

    @SerialName("bathroom")
    Bathroom,

    @SerialName("toilet")
    Toilet,

    @SerialName("custom")
    Custom,
}
