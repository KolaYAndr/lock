package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.data.network.model.SignUpResponse
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.data.network.model.InitialTokenLoadResponse
import org.homesharing.lockbox.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AccountApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {
    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): SignUpResponse =
        withContext(Dispatchers.IO) {
            api.signUp(username, password, passwordRepeated)
        }

    override suspend fun loadTokensInitially(
        username: String,
        password: String
    ): InitialTokenLoadResponse =
        withContext(Dispatchers.IO) {
            api.loadTokensInitially(username, password).also {
                if (it is InitialTokenLoadResponse.InitialTokenLoadSuccessResponse)
                    tokenStorage.apply {
                        saveAccessToken(it.accessToken)
                        saveRefreshToken(it.refreshToken)
                    }
            }
        }
}
