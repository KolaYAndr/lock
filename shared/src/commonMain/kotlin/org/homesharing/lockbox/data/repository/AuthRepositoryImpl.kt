package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.data.network.dto.ConfirmEmailRequestDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.data.network.dto.InitialTokenLoadResponseDto
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AccountApi,
    private val tokenStorage: TokenStorage,
    private val refreshManager: RefreshManager
) : AuthRepository {

    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): SignUpResponseDto =
        withContext(Dispatchers.IO) {
            api.signUp(username, password, passwordRepeated)
        }

    override suspend fun loadTokensInitially(
        username: String,
        password: String
    ): InitialTokenLoadResponseDto =
        withContext(Dispatchers.IO) {
            api.loadTokensInitially(username, password).also { response ->
                if (response is InitialTokenLoadResponseDto.InitialTokenLoadSuccessResponseDto)
                    tokenStorage.apply {
                        saveAccessToken(response.accessToken)
                        saveRefreshToken(response.refreshToken)
                        saveNextRefreshTime(refreshManager.currentTime + response.expiresIn)
                    }
            }
        }

    override suspend fun sendConfirmation(email: String) = withContext(Dispatchers.IO) {
        val body = ConfirmEmailRequestDto(mail = email)
        api.confirmEmail(body)
    }

    override suspend fun checkIfConfirmed(email: String) = withContext(Dispatchers.IO) {

    }
}
