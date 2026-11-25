package org.homesharing.lockbox.data.network.dto.invite_accept

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.guest_invite.GuestInviteDto
import org.homesharing.lockbox.data.network.dto.guest_stage.GuestStageDto

@Serializable
internal data class InviteAcceptResponseDto(
    val stage: GuestStageDto,
    val invite: GuestInviteDto,
    @SerialName("booking_id")
    val bookingId: String,
    @SerialName("contract_id")
    val contractId: String,
)
