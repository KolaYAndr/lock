package org.homesharing.lockbox.domain.model.flat_detail

import org.homesharing.lockbox.data.network.dto.flat_detail.FlatDetailDto
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatStatusDto
import org.homesharing.lockbox.domain.model.premise_type.toDomain
import org.homesharing.lockbox.domain.model.room.toDomain

internal fun FlatDetailDto.toDomain() = FlatDetail(
    id = id,
    name = name,
    description = description,
    premiseType = premiseType.toDomain(),
    address = address.toDomain(),
    fullAddress = fullAddress,
    livingArea = livingArea,
    rooms = rooms.map { it.toDomain() },
    status = status.toDomain(),
    photos = photos.map { it.toDomain() },
    invitesCount = invitesCount,
    activeInvitesCount = activeInvitesCount,
    hasDocuments = hasDocuments,
    invites = invites?.map { it.toDomain() },
)

internal fun FlatDetailDto.AddressDto.toDomain() = FlatDetail.Address(
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

internal fun FlatDetailDto.RoomDto.toDomain() = FlatDetail.Room(
    id = id,
    type = type.toDomain(),
    position = position,
    name = name,
)

internal fun FlatDetailDto.PhotoDto.toDomain() = FlatDetail.Photo(
    id = id,
    url = url,
    position = position,
    fileName = fileName,
)

internal fun FlatDetailDto.InviteListItemDto.toDomain() = FlatDetail.InviteListItem(
    id = id,
    guestName = guestName,
    guestEmail = guestEmail,
    startDate = startDate,
    endDate = endDate,
    status = status,
    emailStatus = emailStatus,
    flatId = flatId,
    flatName = flatName,
    flatAddress = flatAddress,
)

internal fun FlatStatusDto.toDomain(): FlatStatus = when (this) {
    FlatStatusDto.Active -> FlatStatus.Active
    FlatStatusDto.Archived -> FlatStatus.Archived
}
