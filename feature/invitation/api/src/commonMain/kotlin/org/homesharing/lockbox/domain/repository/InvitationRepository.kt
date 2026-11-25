package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.invitation.GuestInvite
import org.homesharing.lockbox.domain.model.invitation.Invite
import org.homesharing.lockbox.domain.model.invitation.InviteAcceptance
import org.homesharing.lockbox.domain.model.invitation.InviteDraft
import org.homesharing.lockbox.domain.model.invitation.InvitePage

interface InvitationRepository {
    suspend fun getInvites(
        limit: Int = 10,
        cursor: String? = null,
    ): Response<InvitePage>

    suspend fun createInvite(request: InviteDraft): Response<Invite>

    suspend fun getInvite(id: String): Response<Invite>

    suspend fun updateInvite(
        id: String,
        request: InviteDraft,
    ): Response<Invite>

    suspend fun deleteInvite(id: String): Response<Unit>

    suspend fun cancelInvite(id: String): Response<Invite>

    suspend fun resendInvite(id: String): Response<Invite>

    suspend fun getGuestInvite(token: String): Response<GuestInvite>

    suspend fun acceptInvite(token: String): Response<InviteAcceptance>

    suspend fun declineInvite(token: String): Response<GuestInvite>
}
