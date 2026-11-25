package org.homesharing.lockbox.domain.model.landlord_dashboard

import org.homesharing.lockbox.data.network.dto.landlord_dashboard.LandlordDashboardDto
import org.homesharing.lockbox.domain.model.stay.toDomain

internal fun LandlordDashboardDto.toDomain() = LandlordDashboard(
    flatsCount = flatsCount,
    archivedFlatsCount = archivedFlatsCount,
    activeInvitesCount = activeInvitesCount,
    activeStays = activeStays.map { it.toDomain() },
    upcomingCheckIns = upcomingCheckIns.map { it.toDomain() },
    upcomingCheckOuts = upcomingCheckOuts.map { it.toDomain() },
)
