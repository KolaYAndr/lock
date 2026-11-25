package org.homesharing.lockbox.domain.model.invite_accept

import org.homesharing.lockbox.domain.model.GuestStage
import org.homesharing.lockbox.domain.model.guest_invite.GuestInvite

data class InviteAccept(
    val stage: GuestStage,
    val invite: GuestInvite,
    val bookingId: String,
    val contractId: String,
)
