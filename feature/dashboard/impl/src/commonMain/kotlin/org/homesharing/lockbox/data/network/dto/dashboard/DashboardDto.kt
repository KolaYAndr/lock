package org.homesharing.lockbox.data.network.dto.dashboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.invitation.GuestInviteDto

@Serializable
internal data class GuestDashboardDto(
    val stage: String,
    @SerialName("contract_status")
    val contractStatus: String? = null,
    @SerialName("contract_url")
    val contractUrl: String? = null,
    val invite: GuestInviteDto? = null,
    @SerialName("invite_token")
    val inviteToken: String? = null,
    val stay: DashboardStayDto? = null,
)

@Serializable
internal data class LandlordDashboardDto(
    @SerialName("active_invites_count")
    val activeInvitesCount: Int,
    @SerialName("active_stays")
    val activeStays: List<DashboardStayDto>,
    @SerialName("archived_flats_count")
    val archivedFlatsCount: Int,
    @SerialName("flats_count")
    val flatsCount: Int,
    @SerialName("upcoming_check_ins")
    val upcomingCheckIns: List<DashboardStayDto>,
    @SerialName("upcoming_check_outs")
    val upcomingCheckOuts: List<DashboardStayDto>,
)

@Serializable
internal data class DashboardStayDto(
    @SerialName("booking_id")
    val bookingId: String,
    @SerialName("contract_status")
    val contractStatus: String,
    @SerialName("end_date")
    val endDate: String,
    @SerialName("flat_address")
    val flatAddress: String,
    @SerialName("flat_id")
    val flatId: String,
    @SerialName("flat_name")
    val flatName: String,
    @SerialName("number_of_guests")
    val numberOfGuests: Int,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("total_price")
    val totalPrice: Int,
    @SerialName("contract_id")
    val contractId: String? = null,
    @SerialName("guest_email")
    val guestEmail: String? = null,
    @SerialName("guest_name")
    val guestName: String? = null,
)
