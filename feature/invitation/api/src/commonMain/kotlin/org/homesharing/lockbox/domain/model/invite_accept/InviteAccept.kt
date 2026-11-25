package org.homesharing.lockbox.domain.model.invite_accept

import org.homesharing.lockbox.domain.model.guest_invite.GuestInvite
import org.homesharing.lockbox.domain.model.guest_stage.GuestStage

data class InviteAccept(
    val stage: GuestStage,
    val invite: GuestInvite,
    val bookingId: String,
    val contractId: String,
)
