package org.homesharing.lockbox.domain.repository

import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.profile_info.ProfileInfo
import org.homesharing.lockbox.domain.model.sessions.Sessions
import org.homesharing.lockbox.domain.model.update_account.UpdateAccount

interface AccountRepository {
    suspend fun logOut()

    val loggedIn: Flow<Boolean>

    fun getImageLink(): String

    suspend fun sendImage(data: ByteArray): Response<Unit>

    suspend fun getSessions(): Response<Sessions>

    suspend fun updateInfo(
        name: String? = null,
        surname: String? = null,
        email: String? = null
    ): Response<UpdateAccount>

    suspend fun deactivateUser(): Response<UpdateAccount>

    suspend fun closeOtherSessions(): Response<Unit>

    suspend fun getProfileInfo(): Response<ProfileInfo>

    suspend fun updateProfileInfo(
        email: String? = null,
        phoneNumber: String? = null,
        fullName: String? = null,
        livingAddress: String? = null,
        passportIssuer: String? = null,
        passportNumber: String? = null,
        passportSeries: String? = null,
    ): Response<Unit>
}
