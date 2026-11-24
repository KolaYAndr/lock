package org.homesharing.lockbox.data.repository

import org.homesharing.lockbox.data.network.api.InvitationApi
import org.homesharing.lockbox.data.network.dto.invite_list.InviteListDto
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
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
}
