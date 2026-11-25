package org.homesharing.lockbox.domain.model.guest_process

import org.homesharing.lockbox.data.network.dto.guest_process.GuestProcessListDto
import org.homesharing.lockbox.domain.model.guest_dashboard.toDomain
import org.homesharing.lockbox.domain.model.stay.toDomain

internal fun GuestProcessListDto.toDomain() = GuestProcessList(
    items = items.map { it.toDomain() },
    nextCursor = nextCursor,
)

internal fun GuestProcessListDto.GuestProcessDto.toDomain() = GuestProcessList.GuestProcess(
    inviteId = inviteId,
    status = status.toDomain(),
    flatName = flatName,
    flatAddress = flatAddress,
    startAt = startAt,
    endAt = endAt,
    stay = stay?.toDomain(),
)
