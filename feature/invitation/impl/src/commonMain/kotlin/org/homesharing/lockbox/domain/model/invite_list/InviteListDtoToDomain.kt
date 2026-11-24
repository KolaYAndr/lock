package org.homesharing.lockbox.domain.model.invite_list

import org.homesharing.lockbox.data.network.dto.invite_list.EmailStatusDto
import org.homesharing.lockbox.data.network.dto.invite_list.InviteListDto
import org.homesharing.lockbox.data.network.dto.invite_list.InviteStatusDto

internal fun InviteListDto.toDomain() = InviteList(
    items = items.map { it.toDomain() },
    nextCursor = nextCursor,
)

internal fun InviteListDto.InviteListItemDto.toDomain() = InviteList.InviteListItem(
    id = id,
    guestEmail = guestEmail,
    guestName = guestName,
    status = status.toDomain(),
    emailStatus = emailStatus.toDomain(),
    flatId = flatId,
    flatName = flatName,
    flatAddress = flatAddress,
    startDate = startDate,
    endDate = endDate,
)

internal fun InviteStatusDto.toDomain(): InviteStatus = when (this) {
    InviteStatusDto.Sent -> InviteStatus.Sent
    InviteStatusDto.Accepted -> InviteStatus.Accepted
    InviteStatusDto.Declined -> InviteStatus.Declined
    InviteStatusDto.Revoked -> InviteStatus.Revoked
    InviteStatusDto.Expired -> InviteStatus.Expired
}

internal fun EmailStatusDto.toDomain(): EmailStatus = when (this) {
    EmailStatusDto.Pending -> EmailStatus.Pending
    EmailStatusDto.Sent -> EmailStatus.Sent
    EmailStatusDto.Failed -> EmailStatus.Failed
}
