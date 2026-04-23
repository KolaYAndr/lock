package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.dto.avatar.AvatarDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto

internal interface AccountApi {
    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): ApiResponse<Unit>

    suspend fun loadTokensInitially(
        username: String,
        password: String
    ): AuthResponse

    suspend fun getAvatar(): ApiResponse<AvatarDto>

    suspend fun sendImage(
        data: ByteArray
    ): ApiResponse<Unit>

    suspend fun getSessions(): ApiResponse<SessionsDto>

    suspend fun confirmEmail(email: String): ApiResponse<ConfirmEmailDto>

    suspend fun updateUserInfo(
        email: String? = null,
        enabled: Boolean? = null,
        firstName: String? = null,
        lastName: String? = null
    ): ApiResponse<UpdateAccountDto>

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
