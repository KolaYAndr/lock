package org.homesharing.lockbox.domain.model.room

import org.homesharing.lockbox.data.network.dto.create_flat.CreateFlatRequestDto
import org.homesharing.lockbox.data.network.dto.room.RoomTypeDto

internal fun RoomInput.toDto() = CreateFlatRequestDto.RoomInputDto(
    type = type.toDto(),
    name = name,
    position = position,
)

internal fun RoomType.toDto(): RoomTypeDto = when (this) {
    RoomType.Kitchen -> RoomTypeDto.Kitchen
    RoomType.Bedroom -> RoomTypeDto.Bedroom
    RoomType.LivingRoom -> RoomTypeDto.LivingRoom
    RoomType.ShowerRoom -> RoomTypeDto.ShowerRoom
    RoomType.Bathroom -> RoomTypeDto.Bathroom
    RoomType.Toilet -> RoomTypeDto.Toilet
    RoomType.Custom -> RoomTypeDto.Custom
}
