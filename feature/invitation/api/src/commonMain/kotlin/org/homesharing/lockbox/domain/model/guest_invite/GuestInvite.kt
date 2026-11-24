package org.homesharing.lockbox.domain.model.guest_invite

import org.homesharing.lockbox.domain.model.invite.Invite
import org.homesharing.lockbox.domain.model.invite.InviteStatus

data class GuestInvite(
    val termsVersion: Long,
    val status: InviteStatus,
    val guest: Invite.Guest,
    val checkIn: Invite.CheckInDetails,
    val settings: Invite.CheckInSettings,
    val hasDocuments: Boolean,
    val expiresAt: String,
    val flatName: String?,
    val flatAddress: String?,
    val landlordName: String?,
    val photos: List<FlatPhoto>,
) {
    data class FlatPhoto(
        val id: String,
        val url: String,
        val position: Int,
        val fileName: String?,
    )
}
