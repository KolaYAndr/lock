package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.data.network.api.AccountApi
import org.homesharing.lockbox.data.network.dto.avatar.AvatarDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto
import org.homesharing.lockbox.data.network.token.TokenStorage
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.avatar.Avatar
import org.homesharing.lockbox.domain.model.profile_info.ProfileInfo
import org.homesharing.lockbox.domain.model.profile_info.toDomain
import org.homesharing.lockbox.domain.model.sessions.Sessions
import org.homesharing.lockbox.domain.model.sessions.toDomain
import org.homesharing.lockbox.domain.model.avatar.toDomain
import org.homesharing.lockbox.domain.model.update_account.UpdateAccount
import org.homesharing.lockbox.domain.model.update_account.toDomain
import org.homesharing.lockbox.domain.repository.AccountRepository

internal class AccountRepositoryImpl(
    private val api: AccountApi,
    private val tokenStorage: TokenStorage,
) : AccountRepository {
    override suspend fun logOut() = tokenStorage.clearInfo()

    override val loggedIn: Flow<Boolean> = tokenStorage.loggedIn

    override suspend fun getImageLink(): Response<Avatar> =
        parseLockboxApiResponse(
            apiCall = { api.getAvatar() },
            mapper = AvatarDto::toDomain
        )

    override suspend fun sendImage(data: ByteArray): Response<Unit> =
        parseLockboxApiResponse {
            api.sendImage(data = data)
        }

    override suspend fun getSessions(): Response<Sessions> =
        parseLockboxApiResponse(
            apiCall = { api.getSessions() },
            mapper = SessionsDto::toDomain
        )

    override suspend fun deactivateUser(): Response<UpdateAccount> =
        parseLockboxApiResponse(
            apiCall = { api.deactivateUser() },
            mapper = UpdateAccountDto::toDomain
        )

    override suspend fun closeOtherSessions(): Response<Unit> {
        return parseLockboxApiResponse(
            apiCall = {
                api.closeOtherSessions()
            },
        )
    }

    override suspend fun getProfileInfo(): Response<ProfileInfo> {
        return parseLockboxApiResponse(
            apiCall = {
                api.getProfileInfo()
            },
            mapper = ProfileInfoDto::toDomain
        )
    }

    override suspend fun updateProfileInfo(
        email: String?,
        phoneNumber: String?,
        fullName: String?,
        livingAddress: String?,
        passportIssuer: String?,
        passportNumber: String?,
        passportSeries: String?
    ): Response<Unit> {
        return parseLockboxApiResponse {
            api.updateProfileInfo(
                email = email,
                phoneNumber = phoneNumber,
                fullName = fullName,
                livingAddress = livingAddress,
                passportIssuer = passportIssuer,
                passportNumber = passportNumber,
                passportSeries = passportSeries,
            )
        }
    }
}
