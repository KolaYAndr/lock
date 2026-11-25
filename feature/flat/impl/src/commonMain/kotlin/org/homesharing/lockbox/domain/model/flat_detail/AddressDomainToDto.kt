package org.homesharing.lockbox.domain.model.flat_detail

import org.homesharing.lockbox.data.network.dto.create_flat.CreateFlatRequestDto

internal fun FlatDetail.Address.toDto() = CreateFlatRequestDto.AddressRequestDto(
    city = city,
    street = street,
    house = house,
    entrance = entrance,
    apartment = apartment,
    block = block,
    building = building,
    litera = litera,
    comment = comment,
)
