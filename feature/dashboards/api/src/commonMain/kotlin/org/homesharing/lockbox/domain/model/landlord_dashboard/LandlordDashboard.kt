package org.homesharing.lockbox.domain.model.landlord_dashboard

import org.homesharing.lockbox.domain.model.stay.StayListItem

data class LandlordDashboard(
    val flatsCount: Int,
    val archivedFlatsCount: Int,
    val activeInvitesCount: Int,
    val activeStays: List<StayListItem>,
    val upcomingCheckIns: List<StayListItem>,
    val upcomingCheckOuts: List<StayListItem>,
)
