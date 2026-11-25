package org.homesharing.lockbox.domain.model.dashboard

import org.homesharing.lockbox.domain.model.invitation.GuestInvite

data class GuestDashboard(
    val stage: String,
    val contractStatus: String? = null,
    val contractUrl: String? = null,
    val invite: GuestInvite? = null,
    val inviteToken: String? = null,
    val stay: DashboardStay? = null,
)

data class LandlordDashboard(
    val activeInvitesCount: Int,
    val activeStays: List<DashboardStay>,
    val archivedFlatsCount: Int,
    val flatsCount: Int,
    val upcomingCheckIns: List<DashboardStay>,
    val upcomingCheckOuts: List<DashboardStay>,
)

data class DashboardStay(
    val bookingId: String,
    val contractStatus: String,
    val endDate: String,
    val flatAddress: String,
    val flatId: String,
    val flatName: String,
    val numberOfGuests: Int,
    val startDate: String,
    val totalPrice: Int,
    val contractId: String? = null,
    val guestEmail: String? = null,
    val guestName: String? = null,
)
