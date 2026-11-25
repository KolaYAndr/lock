package org.homesharing.lockbox.domain.model.invite_accept

import org.homesharing.lockbox.data.network.dto.invite_accept.InviteAcceptResponseDto
import org.homesharing.lockbox.domain.model.guest_invite.toDomain
import org.homesharing.lockbox.domain.model.guest_stage.toDomain

internal fun InviteAcceptResponseDto.toDomain() = InviteAccept(
    stage = stage.toDomain(),
    invite = invite.toDomain(),
    bookingId = bookingId,
    contractId = contractId,
)
