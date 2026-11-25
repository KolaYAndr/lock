package org.homesharing.lockbox.data.repository

import org.homesharing.lockbox.data.network.api.InvitationApi
import org.homesharing.lockbox.data.network.dto.invitation.GuestInviteDto
import org.homesharing.lockbox.data.network.dto.invitation.InviteDto
import org.homesharing.lockbox.data.network.dto.invitation.InviteAcceptanceDto
import org.homesharing.lockbox.data.network.dto.invitation.InvitePageDto
import org.homesharing.lockbox.data.network.dto.invitation.toDomain
import org.homesharing.lockbox.data.network.dto.invitation.toDto
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.invitation.GuestInvite
import org.homesharing.lockbox.domain.model.invitation.Invite
import org.homesharing.lockbox.domain.model.invitation.InviteAcceptance
import org.homesharing.lockbox.domain.model.invitation.InviteDraft
import org.homesharing.lockbox.domain.model.invitation.InvitePage
import org.homesharing.lockbox.domain.repository.InvitationRepository

internal class InvitationRepositoryImpl(
    private val api: InvitationApi,
) : InvitationRepository {
    override suspend fun getInvites(
        limit: Int,
        cursor: String?,
    ): Response<InvitePage> =
        parseLockboxApiResponse({ api.getInvites(limit, cursor) }, InvitePageDto::toDomain)

    override suspend fun createInvite(request: InviteDraft): Response<Invite> =
        parseLockboxApiResponse({ api.createInvite(request.toDto()) }, InviteDto::toDomain)

    override suspend fun getInvite(id: String): Response<Invite> =
        parseLockboxApiResponse({ api.getInvite(id) }, InviteDto::toDomain)

    override suspend fun updateInvite(
        id: String,
        request: InviteDraft,
    ): Response<Invite> =
        parseLockboxApiResponse({ api.updateInvite(id, request.toDto()) }, InviteDto::toDomain)

    override suspend fun deleteInvite(id: String): Response<Unit> =
        parseLockboxApiResponse { api.deleteInvite(id) }

    override suspend fun cancelInvite(id: String): Response<Invite> =
        parseLockboxApiResponse({ api.cancelInvite(id) }, InviteDto::toDomain)

    override suspend fun resendInvite(id: String): Response<Invite> =
        parseLockboxApiResponse({ api.resendInvite(id) }, InviteDto::toDomain)

    override suspend fun getGuestInvite(token: String): Response<GuestInvite> =
        parseLockboxApiResponse({ api.getGuestInvite(token) }, GuestInviteDto::toDomain)

    override suspend fun acceptInvite(token: String): Response<InviteAcceptance> =
        parseLockboxApiResponse({ api.acceptInvite(token) }, InviteAcceptanceDto::toDomain)

    override suspend fun declineInvite(token: String): Response<GuestInvite> =
        parseLockboxApiResponse({ api.declineInvite(token) }, GuestInviteDto::toDomain)
}
