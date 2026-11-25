package org.homesharing.lockbox.data.network.dto.guest_process

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.guest_dashboard.InviteStatusDto
import org.homesharing.lockbox.data.network.dto.stay.StayListItemDto

@Serializable
internal data class GuestProcessListDto(
    val items: List<GuestProcessDto>,
    @SerialName("next_cursor")
    val nextCursor: String?,
) {
    @Serializable
    internal data class GuestProcessDto(
        @SerialName("invite_id")
        val inviteId: String,
        val status: InviteStatusDto,
        @SerialName("flat_name")
        val flatName: String,
        @SerialName("flat_address")
        val flatAddress: String,
        @SerialName("start_at")
        val startAt: String,
        @SerialName("end_at")
        val endAt: String,
        val stay: StayListItemDto?,
    )
}
