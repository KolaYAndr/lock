package org.homesharing.lockbox.domain.model.guest_process

import org.homesharing.lockbox.domain.model.InviteStatus
import org.homesharing.lockbox.domain.model.stay.StayListItem

data class GuestProcessList(
    val items: List<GuestProcess>,
    val nextCursor: String?,
) {
    data class GuestProcess(
        val inviteId: String,
        val status: InviteStatus,
        val flatName: String,
        val flatAddress: String,
        val startAt: String,
        val endAt: String,
        val stay: StayListItem?,
    )
}
