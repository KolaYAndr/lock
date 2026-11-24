package org.homesharing.lockbox.data.network.dto.invite_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.invite.InviteEmailStatusDto
import org.homesharing.lockbox.data.network.dto.invite.InviteStatusDto

@Serializable
internal data class InviteListDto(
    val items: List<InviteListItemDto>,
    @SerialName("next_cursor")
    val nextCursor: String?,
) {
    @Serializable
    internal data class InviteListItemDto(
        val id: String,
        @SerialName("guest_email")
        val guestEmail: String,
        @SerialName("guest_name")
        val guestName: String,
        val status: InviteStatusDto,
        @SerialName("email_status")
        val emailStatus: InviteEmailStatusDto,
        @SerialName("flat_id")
        val flatId: String?,
        @SerialName("flat_name")
        val flatName: String?,
        @SerialName("flat_address")
        val flatAddress: String?,
        @SerialName("start_date")
        val startDate: String,
        @SerialName("end_date")
        val endDate: String,
    )
}
