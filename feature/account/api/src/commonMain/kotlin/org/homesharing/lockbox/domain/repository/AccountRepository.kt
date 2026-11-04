package org.homesharing.lockbox.domain.repository

import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.avatar.Avatar
import org.homesharing.lockbox.domain.model.profile_info.ProfileInfo
import org.homesharing.lockbox.domain.model.sessions.Sessions
import org.homesharing.lockbox.domain.model.update_account.UpdateAccount

interface AccountRepository {
    suspend fun logOut()
    val loggedIn: Flow<Boolean>
    suspend fun getImageLink(): Response<Avatar>
    suspend fun sendImage(data: ByteArray): Response<Unit>
    suspend fun getSessions(): Response<Sessions>
    suspend fun deactivateUser(): Response<UpdateAccount>
    suspend fun closeOtherSessions(): Response<Unit>
    suspend fun getProfileInfo(): Response<ProfileInfo>
    suspend fun updateProfileInfo(
        firstName: String? = null,
        lastName: String? = null,
        middleName: String? = null,
        phoneNumber: String? = null,
        email: String? = null,
        livingAddress: String? = null,
        birthDate: String? = null,
        passportSeries: String? = null,
        passportNumber: String? = null,
        passportIssuer: String? = null,
        passportIssuedAt: String? = null,
        passportDivisionCode: String? = null,
    ): Response<ProfileInfo>
    suspend fun becomeLandlord(): Response<ProfileInfo>
}
