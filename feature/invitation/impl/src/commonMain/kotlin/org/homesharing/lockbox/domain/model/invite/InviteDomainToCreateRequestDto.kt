package org.homesharing.lockbox.domain.model.invite

import org.homesharing.lockbox.data.network.dto.invite.InviteCreateRequestDto

internal fun Invite.Guest.toRequestDto() = InviteCreateRequestDto.GuestRequestDto(
    email = email,
    name = name,
)

internal fun Invite.CheckInDetails.toRequestDto() = InviteCreateRequestDto.CheckInDetailsRequestDto(
    startDate = startDate,
    endDate = endDate,
    numberOfGuests = numberOfGuests,
    checkInTime = checkInTime,
    checkOutTime = checkOutTime,
    prepayment = prepayment,
    deposit = deposit,
    dayPrice = dayPrice,
    comment = comment,
)

internal fun Invite.CheckInSettings.toRequestDto() =
    InviteCreateRequestDto.CheckInSettingsRequestDto(
        requestPassport = requestPassport,
        signContractOnline = signContractOnline,
        roomReport = roomReport,
        exitReport = exitReport,
        intercomCode = intercomCode,
        comment = comment,
    )
