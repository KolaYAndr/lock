package org.homesharing.lockbox.data.network.dto.invitation

import org.homesharing.lockbox.domain.model.invitation.CheckInDetails
import org.homesharing.lockbox.domain.model.invitation.CheckInSettings
import org.homesharing.lockbox.domain.model.invitation.Guest
import org.homesharing.lockbox.domain.model.invitation.GuestInvite
import org.homesharing.lockbox.domain.model.invitation.GuestInvitePhoto
import org.homesharing.lockbox.domain.model.invitation.Invite
import org.homesharing.lockbox.domain.model.invitation.InviteAcceptance
import org.homesharing.lockbox.domain.model.invitation.InviteDraft
import org.homesharing.lockbox.domain.model.invitation.InvitePage
import org.homesharing.lockbox.domain.model.invitation.InviteSummary

fun GuestDto.toDomain(): Guest = Guest(
    email = email,
    name = name,
)

internal fun Guest.toDto(): GuestDto = GuestDto(
    email = email,
    name = name,
)

fun CheckInDetailsDto.toDomain(): CheckInDetails = CheckInDetails(
    checkInTime = checkInTime,
    checkOutTime = checkOutTime,
    comment = comment,
    dayPrice = dayPrice,
    deposit = deposit,
    endDate = endDate,
    numberOfGuests = numberOfGuests,
    startDate = startDate,
    totalPrice = totalPrice,
)

internal fun CheckInDetails.toDto(): CheckInDetailsDto = CheckInDetailsDto(
    checkInTime = checkInTime,
    checkOutTime = checkOutTime,
    comment = comment,
    dayPrice = dayPrice,
    deposit = deposit,
    endDate = endDate,
    numberOfGuests = numberOfGuests,
    startDate = startDate,
    totalPrice = totalPrice,
)

fun CheckInSettingsDto.toDomain(): CheckInSettings = CheckInSettings(
    comment = comment,
    exitReport = exitReport,
    intercomCode = intercomCode,
    requestPassport = requestPassport,
    roomReport = roomReport,
    signContractOnline = signContractOnline,
)

internal fun CheckInSettings.toDto(): CheckInSettingsDto = CheckInSettingsDto(
    comment = comment,
    exitReport = exitReport,
    intercomCode = intercomCode,
    requestPassport = requestPassport,
    roomReport = roomReport,
    signContractOnline = signContractOnline,
)

internal fun InviteDraft.toDto(): InviteDraftDto = InviteDraftDto(
    checkIn = checkIn.toDto(),
    flatId = flatId,
    guest = guest.toDto(),
    settings = settings?.toDto(),
)

internal fun InviteDto.toDomain(): Invite = Invite(
    acceptedAt = acceptedAt,
    bookingId = bookingId,
    checkIn = checkIn.toDomain(),
    declinedAt = declinedAt,
    emailStatus = emailStatus,
    expiresAt = expiresAt,
    flatId = flatId,
    guest = guest.toDomain(),
    id = id,
    inviteUrl = inviteUrl,
    settings = settings.toDomain(),
    status = status,
)

internal fun InviteSummaryDto.toDomain(): InviteSummary = InviteSummary(
    emailStatus = emailStatus,
    endDate = endDate,
    guestEmail = guestEmail,
    guestName = guestName,
    id = id,
    startDate = startDate,
    status = status,
)

internal fun InvitePageDto.toDomain(): InvitePage = InvitePage(
    items = items.map(InviteSummaryDto::toDomain),
    nextCursor = nextCursor,
)

fun GuestInvitePhotoDto.toDomain(): GuestInvitePhoto = GuestInvitePhoto(
    fileName = fileName,
    id = id,
    position = position,
    url = url,
)

fun GuestInviteDto.toDomain(): GuestInvite = GuestInvite(
    checkIn = checkIn.toDomain(),
    expiresAt = expiresAt,
    flatAddress = flatAddress,
    flatName = flatName,
    guest = guest.toDomain(),
    landlordName = landlordName,
    photos = photos.map(GuestInvitePhotoDto::toDomain),
    settings = settings.toDomain(),
    status = status,
)

internal fun InviteAcceptanceDto.toDomain(): InviteAcceptance = InviteAcceptance(
    bookingId = bookingId,
    contractId = contractId,
    invite = invite.toDomain(),
)
