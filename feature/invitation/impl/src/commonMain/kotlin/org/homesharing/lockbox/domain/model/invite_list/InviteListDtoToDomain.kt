package org.homesharing.lockbox.domain.model.invite_list

import org.homesharing.lockbox.data.network.dto.invite_list.InviteListDto
import org.homesharing.lockbox.domain.model.invite.toDomain

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
