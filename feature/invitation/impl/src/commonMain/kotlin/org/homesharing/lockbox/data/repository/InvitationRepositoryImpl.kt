package org.homesharing.lockbox.data.repository

import org.homesharing.lockbox.data.network.api.InvitationApi
import org.homesharing.lockbox.data.network.dto.invite.InviteCreateRequestDto
import org.homesharing.lockbox.data.network.dto.invite.InviteDto
import org.homesharing.lockbox.data.network.dto.invite_list.InviteListDto
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.invite.Invite
import org.homesharing.lockbox.domain.model.invite.toDomain
import org.homesharing.lockbox.domain.model.invite.toRequestDto
import org.homesharing.lockbox.domain.model.invite_list.InviteList
import org.homesharing.lockbox.domain.model.invite_list.toDomain
import org.homesharing.lockbox.domain.repository.InvitationRepository

internal class InvitationRepositoryImpl(
    private val api: InvitationApi,
) : InvitationRepository {
    override suspend fun getInvites(
        flatId: String?,
        limit: Int,
        cursor: String?,
    ): Response<InviteList> {
        return parseLockboxApiResponse(
            apiCall = { api.getInvites(flatId = flatId, limit = limit, cursor = cursor) },
            mapper = InviteListDto::toDomain,
        )
    }

    override suspend fun getInvite(id: String): Response<Invite> {
        return parseLockboxApiResponse(
            apiCall = { api.getInvite(id) },
            mapper = InviteDto::toDomain,
        )
    }

    override suspend fun updateInvite(
        id: String,
        flatId: String,
        guest: Invite.Guest,
        checkIn: Invite.CheckInDetails,
        settings: Invite.CheckInSettings?,
    ): Response<Invite> {
        val request = InviteCreateRequestDto(
            flatId = flatId,
            guest = guest.toRequestDto(),
            checkIn = checkIn.toRequestDto(),
            settings = settings?.toRequestDto(),
        )

        return parseLockboxApiResponse(
            apiCall = { api.updateInvite(id, request) },
            mapper = InviteDto::toDomain,
        )
    }

    override suspend fun deleteInvite(id: String): Response<Unit> {
        return parseLockboxApiResponse {
            api.deleteInvite(id)
        }
    }

    override suspend fun createInvite(
        flatId: String,
        guest: Invite.Guest,
        checkIn: Invite.CheckInDetails,
        settings: Invite.CheckInSettings?,
    ): Response<Invite> {
        val request = InviteCreateRequestDto(
            flatId = flatId,
            guest = guest.toRequestDto(),
            checkIn = checkIn.toRequestDto(),
            settings = settings?.toRequestDto(),
        )

        return parseLockboxApiResponse(
            apiCall = { api.createInvite(request) },
            mapper = InviteDto::toDomain,
        )
    }
}
