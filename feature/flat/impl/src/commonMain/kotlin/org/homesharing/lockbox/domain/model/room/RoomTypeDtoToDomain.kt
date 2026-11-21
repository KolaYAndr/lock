package org.homesharing.lockbox.domain.model.room

import org.homesharing.lockbox.data.network.dto.room.RoomTypeDto

internal fun RoomTypeDto.toDomain(): RoomType = when (this) {
    RoomTypeDto.Kitchen -> RoomType.Kitchen
    RoomTypeDto.Bedroom -> RoomType.Bedroom
    RoomTypeDto.LivingRoom -> RoomType.LivingRoom
    RoomTypeDto.ShowerRoom -> RoomType.ShowerRoom
    RoomTypeDto.Bathroom -> RoomType.Bathroom
    RoomTypeDto.Toilet -> RoomType.Toilet
    RoomTypeDto.Custom -> RoomType.Custom
}
