package org.homesharing.lockbox.domain.model.guest_invite

import org.homesharing.lockbox.data.network.dto.guest_invite.GuestInviteDto
import org.homesharing.lockbox.domain.model.invite.toDomain

internal fun GuestInviteDto.toDomain() = GuestInvite(
    termsVersion = termsVersion,
    status = status.toDomain(),
    guest = guest.toDomain(),
    checkIn = checkIn.toDomain(),
    settings = settings.toDomain(),
    hasDocuments = hasDocuments,
    expiresAt = expiresAt,
    flatName = flatName,
    flatAddress = flatAddress,
    landlordName = landlordName,
    photos = photos.map { it.toDomain() },
)

internal fun GuestInviteDto.FlatPhotoDto.toDomain() = GuestInvite.FlatPhoto(
    id = id,
    url = url,
    position = position,
    fileName = fileName,
)
