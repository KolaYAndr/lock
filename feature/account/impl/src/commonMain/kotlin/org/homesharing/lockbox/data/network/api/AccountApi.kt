package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.avatar.AvatarDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto

internal interface AccountApi {
    suspend fun getAvatar(): ApiResponse<AvatarDto>

    suspend fun sendImage(
        data: ByteArray
    ): ApiResponse<Unit>

    suspend fun getSessions(): ApiResponse<SessionsDto>

    suspend fun deactivateUser(): ApiResponse<UpdateAccountDto>

    suspend fun closeOtherSessions(): ApiResponse<Unit>

    suspend fun getProfileInfo(): ApiResponse<ProfileInfoDto>

    suspend fun updateProfileInfo(
        email: String? = null,
        phoneNumber: String? = null,
        fullName: String? = null,
        livingAddress: String? = null,
        passportIssuer: String? = null,
        passportNumber: String? = null,
        passportSeries: String? = null,
    ): ApiResponse<Unit>
}
