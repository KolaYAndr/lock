package org.homesharing.lockbox.data.network.dto.landlord_dashboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.stay.StayListItemDto

@Serializable
internal data class LandlordDashboardDto(
    @SerialName("flats_count")
    val flatsCount: Int,
    @SerialName("archived_flats_count")
    val archivedFlatsCount: Int,
    @SerialName("active_invites_count")
    val activeInvitesCount: Int,
    @SerialName("active_stays")
    val activeStays: List<StayListItemDto>,
    @SerialName("upcoming_check_ins")
    val upcomingCheckIns: List<StayListItemDto>,
    @SerialName("upcoming_check_outs")
    val upcomingCheckOuts: List<StayListItemDto>,
)
