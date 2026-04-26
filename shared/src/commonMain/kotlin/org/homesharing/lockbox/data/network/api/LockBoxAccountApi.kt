package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
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
import org.homesharing.lockbox.data.network.dto.avatar.AvatarDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailRequest
import org.homesharing.lockbox.data.network.dto.profile_info.ContactInfoRequest
import org.homesharing.lockbox.data.network.dto.profile_info.LegalInfoRequest
import org.homesharing.lockbox.data.network.dto.profile_info.PassportRequest
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoRequest
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpRequest
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountRequest

private const val ACCOUNTS = "accounts"
private const val USERS = "users"
private const val AVATARS = "avatars"
private const val SIGN_UP = "signup"
private const val SESSIONS = "sessions"
private const val LOGOUT = "logout"
private const val UPLOAD = "upload"
private const val DOWNLOAD = "download"
private const val MAIL = "mail"
private const val SEND = "send"
private const val ME = "me"
private const val PASSWORD = "password"
private const val USERNAME = "username"
private const val OFFLINE_ACCESS_SCOPE = "openid offline_access"
private const val CLIENT_ID = "client_id"
private const val WEB_FRONT = "web-front"
private const val GRANT_TYPE = "grant_type"
private const val SCOPE = "scope"
private const val PROFILE_INFO = "profile-info"

private const val SIGN_UP_URL = "$KOTLIN_BASE_ENDPOINT/$USERS/$SIGN_UP"
private const val SESSIONS_URL = "$GO_BASE_ENDPOINT/$SESSIONS"
private const val AVATARS_POST_URL = "$GO_BASE_ENDPOINT/$AVATARS/$UPLOAD"
private const val AVATARS_URL = "$GO_BASE_ENDPOINT/$AVATARS/$DOWNLOAD"
private const val CONFIRM_EMAIL_URL = "$KEYCLOACK_BASE_ENDPOINT:8445/$ACCOUNTS/$MAIL/$SEND"
private const val UPDATE_ACCOUNT_URL = "$KEYCLOACK_BASE_ENDPOINT:8445/$ACCOUNTS/$ME"
private const val PROFILE_INFO_URL = "$GO_BASE_ENDPOINT/$PROFILE_INFO"

internal class LockBoxAccountApi(
    private val client: HttpClient,
    private val json: Json,
) : AccountApi {
    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String,
    ): ApiResponse<Unit> {
        val request = SignUpRequest(
            email = username,
            password = password,
            passwordRepeat = passwordRepeated,
        )
        return client.post {
            url(SIGN_UP_URL)
            setBody(request)
        }.toApiResponse<Unit>(json)
    }

    override suspend fun getAvatar(): ApiResponse<AvatarDto> {
        return client.get {
            url(AVATARS_URL)
        }.toApiResponse<AvatarDto>(json)
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

    override suspend fun sendImage(data: ByteArray): ApiResponse<Unit> {
        return client.submitFormWithBinaryData(
            url = AVATARS_POST_URL,
            formData = formData {
                append(
                    key = "avatar",
                    value = data,
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=\"avatar.png\"")
                    }
                )
            }
        ).toApiResponse<Unit>(json)
    }

    override suspend fun getSessions(): ApiResponse<SessionsDto> =
        client.get {
            url(SESSIONS_URL)
        }.toApiResponse<SessionsDto>(json)

    override suspend fun confirmEmail(email: String): ApiResponse<ConfirmEmailDto> {
        val request = ConfirmEmailRequest(email)
        return client.post {
            url(CONFIRM_EMAIL_URL)
            setBody(request)
        }.toApiResponse<ConfirmEmailDto>(json)
    }

    override suspend fun deactivateUser(): ApiResponse<UpdateAccountDto> {
        val request = UpdateAccountRequest(null, false, null, null)
        return client.patch {
            url(UPDATE_ACCOUNT_URL)
            setBody(request)
        }.toApiResponse<UpdateAccountDto>(json)
    }

    override suspend fun closeOtherSessions(): ApiResponse<Unit> {
        return client.post {
            url("$SESSIONS_URL/$LOGOUT")
        }.toApiResponse<Unit>(json)
    }

    override suspend fun getProfileInfo(): ApiResponse<ProfileInfoDto> {
        return client.get {
            url(PROFILE_INFO_URL)
        }.toApiResponse<ProfileInfoDto>(json)
    }

    override suspend fun updateProfileInfo(
        email: String?,
        phoneNumber: String?,
        fullName: String?,
        livingAddress: String?,
        passportIssuer: String?,
        passportNumber: String?,
        passportSeries: String?
    ): ApiResponse<Unit> {
        val request = ProfileInfoRequest(
            contactInfo = ContactInfoRequest(
                email = email,
                phoneNumber = phoneNumber
            ).takeIf { email != null || passportNumber != null },
            legalInfo = LegalInfoRequest(
                fullName = fullName,
                livingAddress = livingAddress,
                passport = PassportRequest(
                    issuer = passportIssuer,
                    passportNumber = passportNumber,
                    passportSeries = passportSeries
                ).takeIf { passportSeries != null || passportNumber != null || passportIssuer != null }
            ).takeIf { fullName != null || livingAddress != null || passportSeries != null || passportNumber != null || passportIssuer != null }
        )
        return client.post {
            url(PROFILE_INFO_URL)
            setBody(request)
        }.toApiResponse<Unit>(json)
    }
}
