package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.homesharing.lockbox.data.network.api.AuthApi
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.login.LoginRequestDto
import org.homesharing.lockbox.data.network.dto.otp.OtpResponseDto
import org.homesharing.lockbox.data.network.dto.password.ChangePasswordRequestDto
import org.homesharing.lockbox.data.network.dto.password_reset.PasswordResetConfirmRequestDto
import org.homesharing.lockbox.data.network.dto.password_reset.PasswordResetRequestDto
import org.homesharing.lockbox.data.network.dto.register.RegisterRequestDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto
import org.homesharing.lockbox.data.network.token.TokenStorage
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.auth.AuthEntryType
import org.homesharing.lockbox.domain.model.otp.OtpResponse
import org.homesharing.lockbox.domain.model.otp.OtpVerifyResult
import org.homesharing.lockbox.domain.model.otp.toDomain
import org.homesharing.lockbox.domain.model.sessions.Sessions
import org.homesharing.lockbox.domain.model.sessions.toDomain
import org.homesharing.lockbox.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenStorage: TokenStorage,
) : AuthRepository {
    private val _authEntryType = MutableStateFlow<AuthEntryType?>(null)
    override val authEntryType: StateFlow<AuthEntryType?> = _authEntryType.asStateFlow()

    override fun clearAuthEntryType() {
        _authEntryType.value = null
    }

    override suspend fun register(email: String, password: String): Response<Unit> {
        return try {
            when (val response = api.register(RegisterRequestDto(email = email, password = password))) {
                is ApiResponse.Success -> {
                    _authEntryType.value = AuthEntryType.NewUser
                    tokenStorage.saveTokens(response.data.accessToken, response.data.refreshToken)
                    Response.Success(Unit)
                }
                is ApiResponse.Failure -> Response.Failure(response.message)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.message.orEmpty())
        }
    }

    override suspend fun login(email: String, password: String): Response<Unit> {
        return try {
            when (val response = api.login(LoginRequestDto(email = email, password = password))) {
                is ApiResponse.Success -> {
                    _authEntryType.value = AuthEntryType.ExistingUser
                    tokenStorage.saveTokens(response.data.accessToken, response.data.refreshToken)
                    Response.Success(Unit)
                }
                is ApiResponse.Failure -> Response.Failure(response.message)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.message.orEmpty())
        }
    }

    override suspend fun requestOtp(email: String): Response<OtpResponse> =
        parseLockboxApiResponse(
            apiCall = { api.requestOtp(email) },
            mapper = OtpResponseDto::toDomain
        )

    override suspend fun verifyOtp(email: String, code: String): Response<OtpVerifyResult> {
        return try {
            when (val response = api.verifyOtp(email, code)) {
                is ApiResponse.Success -> {
                    val result = response.data.toDomain()
                    _authEntryType.value = if (result.isNewUser) {
                        AuthEntryType.NewUser
                    } else {
                        AuthEntryType.ExistingUser
                    }
                    tokenStorage.saveTokens(response.data.accessToken, response.data.refreshToken)
                    Response.Success(result)
                }
                is ApiResponse.Failure -> Response.Failure(response.message)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.message.orEmpty())
        }
    }

    override suspend fun requestPasswordReset(email: String): Response<OtpResponse> =
        parseLockboxApiResponse(
            apiCall = { api.requestPasswordReset(PasswordResetRequestDto(email = email)) },
            mapper = OtpResponseDto::toDomain
        )

    override suspend fun confirmPasswordReset(
        email: String,
        code: String,
        newPassword: String,
    ): Response<Unit> = parseLockboxApiResponse(
        apiCall = {
            api.confirmPasswordReset(
                PasswordResetConfirmRequestDto(
                    email = email,
                    code = code,
                    newPassword = newPassword,
                )
            )
        }
    )

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
    ): Response<Unit> = parseLockboxApiResponse(
        apiCall = {
            api.changePassword(
                ChangePasswordRequestDto(
                    currentPassword = currentPassword,
                    newPassword = newPassword,
                )
            )
        }
    )

    override suspend fun getSessions(): Response<Sessions> =
        parseLockboxApiResponse(
            apiCall = { api.getSessions() },
            mapper = SessionsDto::toDomain
        )

    override suspend fun closeOtherSessions(): Response<Unit> =
        parseLockboxApiResponse {
            api.closeOtherSessions()
        }

    override suspend fun logout(): Response<Unit> {
        return try {
            val refreshToken = tokenStorage.getRefreshToken()
            when (val response = api.logout(refreshToken)) {
                is ApiResponse.Success -> Response.Success(Unit)
                is ApiResponse.Failure -> Response.Failure(response.message)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Response.Failure(e.message.orEmpty())
        } finally {
            _authEntryType.value = null
            tokenStorage.clearInfo()
        }
    }
}
