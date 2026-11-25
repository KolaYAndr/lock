package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.invitation.GuestInviteDto
import org.homesharing.lockbox.data.network.dto.invitation.InviteDto
import org.homesharing.lockbox.data.network.dto.invitation.InviteAcceptanceDto
import org.homesharing.lockbox.data.network.dto.invitation.InviteDraftDto
import org.homesharing.lockbox.data.network.dto.invitation.InvitePageDto

internal interface InvitationApi {
    suspend fun getInvites(
        limit: Int,
        cursor: String?,
    ): ApiResponse<InvitePageDto>

    suspend fun createInvite(request: InviteDraftDto): ApiResponse<InviteDto>

    suspend fun getInvite(id: String): ApiResponse<InviteDto>

    suspend fun updateInvite(
        id: String,
        request: InviteDraftDto,
    ): ApiResponse<InviteDto>

    suspend fun deleteInvite(id: String): ApiResponse<Unit>

    suspend fun cancelInvite(id: String): ApiResponse<InviteDto>

    suspend fun resendInvite(id: String): ApiResponse<InviteDto>

    suspend fun getGuestInvite(token: String): ApiResponse<GuestInviteDto>

    suspend fun acceptInvite(token: String): ApiResponse<InviteAcceptanceDto>

    suspend fun declineInvite(token: String): ApiResponse<GuestInviteDto>
}
