package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.parameters
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.api.response.toAuthResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.client.KEYCLOACK_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.client.KOTLIN_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.client.TOKEN_URL
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailRequestDto
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
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpRequestDto
import org.homesharing.lockbox.data.network.dto.token.TokenResponseDto

private const val ACCOUNTS = "accounts"
private const val USERS = "users"
private const val SIGN_UP = "signup"
private const val MAIL = "mail"
private const val SEND = "send"
private const val PASSWORD = "password"
private const val USERNAME = "username"
private const val OFFLINE_ACCESS_SCOPE = "openid offline_access"
private const val CLIENT_ID = "client_id"
private const val WEB_FRONT = "web-front"
private const val GRANT_TYPE = "grant_type"
private const val SCOPE = "scope"
private const val AUTH = "auth"
private const val OTP = "otp"
private const val REQUEST = "request"
private const val VERIFY = "verify"
private const val RESET = "reset"
private const val CONFIRM = "confirm"
private const val LOGOUT = "logout"
private const val REGISTER = "register"
private const val LOGIN = "login"

private const val SIGN_UP_URL = "$KOTLIN_BASE_ENDPOINT/$USERS/$SIGN_UP"
private const val CONFIRM_EMAIL_URL = "$KEYCLOACK_BASE_ENDPOINT:8445/$ACCOUNTS/$MAIL/$SEND"
private const val OTP_REQUEST_URL = "$GO_BASE_ENDPOINT/$AUTH/$OTP/$REQUEST"
private const val OTP_VERIFY_URL = "$GO_BASE_ENDPOINT/$AUTH/$OTP/$VERIFY"
private const val PASSWORD_RESET_REQUEST_URL = "$GO_BASE_ENDPOINT/$AUTH/$PASSWORD/$RESET/$REQUEST"
private const val PASSWORD_RESET_CONFIRM_URL = "$GO_BASE_ENDPOINT/$AUTH/$PASSWORD/$RESET/$CONFIRM"
private const val CHANGE_PASSWORD_URL = "$GO_BASE_ENDPOINT/$AUTH/$PASSWORD"
private const val LOGOUT_URL = "$GO_BASE_ENDPOINT/$AUTH/$LOGOUT"
private const val REGISTER_URL = "$GO_BASE_ENDPOINT/$AUTH/$REGISTER"
private const val LOGIN_URL = "$GO_BASE_ENDPOINT/$AUTH/$LOGIN"

internal class AuthApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : AuthApi {
    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String,
    ): ApiResponse<Unit> {
        val request = SignUpRequestDto(
            email = username,
            password = password,
            passwordRepeat = passwordRepeated,
        )
        return client.post {
            url(SIGN_UP_URL)
            setBody(request)
        }.toApiResponse<Unit>(json)
    }

    override suspend fun register(
        request: RegisterRequestDto,
    ): ApiResponse<TokenResponseDto> {
        return client.post {
            url(REGISTER_URL)
            setBody(request)
        }.toApiResponse<TokenResponseDto>(json)
    }

    override suspend fun loadTokensInitially(
        username: String,
        password: String,
    ): AuthResponse = client.submitForm(
        url = TOKEN_URL,
        formParameters = parameters {
            append(GRANT_TYPE, PASSWORD)
            append(CLIENT_ID, WEB_FRONT)
            append(USERNAME, username)
            append(PASSWORD, password)
            append(SCOPE, OFFLINE_ACCESS_SCOPE)
        }
    ).toAuthResponse(json)

    override suspend fun login(
        request: LoginRequestDto,
    ): ApiResponse<TokenResponseDto> {
        return client.post {
            url(LOGIN_URL)
            setBody(request)
        }.toApiResponse<TokenResponseDto>(json)
    }

    override suspend fun confirmEmail(email: String): ApiResponse<ConfirmEmailDto> {
        val request = ConfirmEmailRequestDto(email)
        return client.post {
            url(CONFIRM_EMAIL_URL)
            setBody(request)
        }.toApiResponse<ConfirmEmailDto>(json)
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

    override suspend fun logout(refreshToken: String): ApiResponse<Unit> {
        val request = LogoutRequestDto(refreshToken = refreshToken)
        return client.post {
            url(LOGOUT_URL)
            setBody(request)
        }.toApiResponse<Unit>(json)
    }
}
