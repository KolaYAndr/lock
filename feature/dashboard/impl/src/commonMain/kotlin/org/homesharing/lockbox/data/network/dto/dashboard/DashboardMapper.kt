package org.homesharing.lockbox.data.network.dto.dashboard

import org.homesharing.lockbox.data.network.dto.invitation.toDomain
import org.homesharing.lockbox.domain.model.dashboard.DashboardStay
import org.homesharing.lockbox.domain.model.dashboard.GuestDashboard
import org.homesharing.lockbox.domain.model.dashboard.LandlordDashboard

internal fun GuestDashboardDto.toDomain(): GuestDashboard = GuestDashboard(
    contractStatus = contractStatus,
    contractUrl = contractUrl,
    invite = invite?.toDomain(),
    inviteToken = inviteToken,
    stage = stage,
    stay = stay?.toDomain(),
)

internal fun LandlordDashboardDto.toDomain(): LandlordDashboard = LandlordDashboard(
    activeInvitesCount = activeInvitesCount,
    activeStays = activeStays.map(DashboardStayDto::toDomain),
    archivedFlatsCount = archivedFlatsCount,
    flatsCount = flatsCount,
    upcomingCheckIns = upcomingCheckIns.map(DashboardStayDto::toDomain),
    upcomingCheckOuts = upcomingCheckOuts.map(DashboardStayDto::toDomain),
)

internal fun DashboardStayDto.toDomain(): DashboardStay = DashboardStay(
    bookingId = bookingId,
    contractId = contractId,
    contractStatus = contractStatus,
    endDate = endDate,
    flatAddress = flatAddress,
    flatId = flatId,
    flatName = flatName,
    guestEmail = guestEmail,
    guestName = guestName,
    numberOfGuests = numberOfGuests,
    startDate = startDate,
    totalPrice = totalPrice,
)
