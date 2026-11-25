package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.dto.login.LoginRequestDto
import org.homesharing.lockbox.data.network.dto.logout.LogoutRequestDto
import org.homesharing.lockbox.data.network.dto.otp.OtpRequestDto
import org.homesharing.lockbox.data.network.dto.otp.OtpResponseDto
import org.homesharing.lockbox.data.network.dto.otp.OtpVerifyRequestDto
import org.homesharing.lockbox.data.network.dto.otp.OtpVerifyResponseDto
import org.homesharing.lockbox.data.network.dto.password.ChangePasswordRequestDto
import org.homesharing.lockbox.data.network.dto.password_reset.PasswordResetConfirmRequestDto
import org.homesharing.lockbox.data.network.dto.password_reset.PasswordResetRequestDto
import org.homesharing.lockbox.data.network.dto.register.RegisterRequestDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto
import org.homesharing.lockbox.data.network.dto.token.TokenResponseDto

private const val PASSWORD = "password"
private const val AUTH = "auth"
private const val OTP = "otp"
private const val REQUEST = "request"
private const val VERIFY = "verify"
private const val RESET = "reset"
private const val CONFIRM = "confirm"
private const val LOGOUT = "logout"
private const val REGISTER = "register"
private const val LOGIN = "login"
private const val SESSIONS = "sessions"

private const val OTP_REQUEST_URL = "$AUTH/$OTP/$REQUEST"
private const val OTP_VERIFY_URL = "$AUTH/$OTP/$VERIFY"
private const val PASSWORD_RESET_REQUEST_URL = "$AUTH/$PASSWORD/$RESET/$REQUEST"
private const val PASSWORD_RESET_CONFIRM_URL = "$AUTH/$PASSWORD/$RESET/$CONFIRM"
private const val CHANGE_PASSWORD_URL = "$AUTH/$PASSWORD"
private const val LOGOUT_URL = "$AUTH/$LOGOUT"
private const val REGISTER_URL = "$AUTH/$REGISTER"
private const val LOGIN_URL = "$AUTH/$LOGIN"
private const val SESSIONS_URL = SESSIONS
private const val CLOSE_OTHER_SESSIONS_URL = "$SESSIONS_URL/$LOGOUT"

internal class AuthApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : AuthApi {
    override suspend fun register(
        request: RegisterRequestDto,
    ): ApiResponse<TokenResponseDto> {
        return client.post {
            url(REGISTER_URL)
            setBody(request)
        }.toApiResponse<TokenResponseDto>(json)
    }

    override suspend fun login(
        request: LoginRequestDto,
    ): ApiResponse<TokenResponseDto> {
        return client.post {
            url(LOGIN_URL)
            setBody(request)
        }.toApiResponse<TokenResponseDto>(json)
    }

    override suspend fun requestOtp(email: String): ApiResponse<OtpResponseDto> {
        val request = OtpRequestDto(email)
        return client.post {
            url(OTP_REQUEST_URL)
            setBody(request)
        }.toApiResponse<OtpResponseDto>(json)
    }

    override suspend fun verifyOtp(email: String, code: String): ApiResponse<OtpVerifyResponseDto> {
        val request = OtpVerifyRequestDto(email = email, code = code)
        return client.post {
            url(OTP_VERIFY_URL)
            setBody(request)
        }.toApiResponse<OtpVerifyResponseDto>(json)
    }

    override suspend fun requestPasswordReset(
        request: PasswordResetRequestDto,
    ): ApiResponse<OtpResponseDto> {
        return client.post {
            url(PASSWORD_RESET_REQUEST_URL)
            setBody(request)
        }.toApiResponse<OtpResponseDto>(json)
    }

    override suspend fun confirmPasswordReset(
        request: PasswordResetConfirmRequestDto,
    ): ApiResponse<Unit> {
        return client.post {
            url(PASSWORD_RESET_CONFIRM_URL)
            setBody(request)
        }.toApiResponse<Unit>(json)
    }

    override suspend fun changePassword(
        request: ChangePasswordRequestDto,
    ): ApiResponse<Unit> {
        return client.post {
            url(CHANGE_PASSWORD_URL)
            setBody(request)
        }.toApiResponse<Unit>(json)
    }

    override suspend fun getSessions(): ApiResponse<SessionsDto> =
        client.get {
            url(SESSIONS_URL)
        }.toApiResponse<SessionsDto>(json)

    override suspend fun closeOtherSessions(): ApiResponse<Unit> =
        client.post {
            url(CLOSE_OTHER_SESSIONS_URL)
        }.toApiResponse<Unit>(json)

    override suspend fun logout(refreshToken: String): ApiResponse<Unit> {
        val request = LogoutRequestDto(refreshToken = refreshToken)
        return client.post {
            url(LOGOUT_URL)
            setBody(request)
        }.toApiResponse<Unit>(json)
    }
}
