package org.homesharing.lockbox.domain.model.invite_list

import org.homesharing.lockbox.domain.model.invite.InviteEmailStatus
import org.homesharing.lockbox.domain.model.invite.InviteStatus

data class InviteList(
    val items: List<InviteListItem>,
    val nextCursor: String?,
) {
    data class InviteListItem(
        val id: String,
        val guestEmail: String,
        val guestName: String,
        val status: InviteStatus,
        val emailStatus: InviteEmailStatus,
        val flatId: String?,
        val flatName: String?,
        val flatAddress: String?,
        val startDate: String,
        val endDate: String,
    )
}
