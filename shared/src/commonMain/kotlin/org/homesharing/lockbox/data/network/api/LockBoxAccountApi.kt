package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
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
import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsDto
import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsRequest
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailRequest
import org.homesharing.lockbox.data.network.dto.post_image.PostImageDto
import org.homesharing.lockbox.data.network.dto.profile_info.ContactInfoRequest
import org.homesharing.lockbox.data.network.dto.profile_info.LegalInfoRequest
import org.homesharing.lockbox.data.network.dto.profile_info.PassportRequest
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoRequest
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpRequest
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountRequest
import org.homesharing.lockbox.domain.api.AccountApi

private const val REALM = "realms/lockbox"
private const val PROTOCOL = "protocol"
private const val OPENID_CONNECT_TOKEN = "openid-connect/token"
private const val ACCOUNTS = "accounts"
private const val USER = "user"
private const val USERS = "users"
private const val AVATAR = "avatar"
private const val SIGN_UP = "signup"
private const val SESSIONS = "sessions"
private const val UPLOAD = "upload"
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

internal const val TOKEN_URL =
    "$KEYCLOACK_BASE_ENDPOINT:8443/$REALM/$PROTOCOL/$OPENID_CONNECT_TOKEN"
internal const val SIGN_UP_URL = "$KOTLIN_BASE_ENDPOINT/$USERS/$SIGN_UP"
internal const val SESSIONS_URL = "$KEYCLOACK_BASE_ENDPOINT:8445/$ACCOUNTS/$SESSIONS"
internal const val AVATARS_POST_URL = "$KEYCLOACK_BASE_ENDPOINT:8445/$USER/$AVATAR/$UPLOAD"
internal const val CONFIRM_EMAIL_URL = "$KEYCLOACK_BASE_ENDPOINT:8445/$ACCOUNTS/$MAIL/$SEND"
internal const val UPDATE_ACCOUNT_URL = "$KEYCLOACK_BASE_ENDPOINT:8445/$ACCOUNTS/$ME"
internal const val AVATARS_URL = "$KEYCLOACK_BASE_ENDPOINT:9000/$USER/$AVATAR"
internal const val PROFILE_INFO_URL = "$GO_BASE_ENDPOINT/$PROFILE_INFO"

internal class LockBoxAccountApi(
    private val client: HttpClient,
    private val json: Json,
) : AccountApi {

    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String,
    ): ApiResponse {
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

    override fun getImageLink(sub: String): String = "$AVATARS_URL/$sub"

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

    override suspend fun sendImage(data: ByteArray): ApiResponse {
        val response = client.post {
            url(AVATARS_POST_URL)
        }.toApiResponse<PostImageDto>(json)

        return if (response is ApiResponse.Success<*>) {
            val link = (response.data as PostImageDto).link
            client.put {
                url(link)
                header(HttpHeaders.ContentType, ContentType.Image.PNG)
                setBody(data)
            }.toApiResponse<Unit>(json)
        } else {
            response as ApiResponse.Failure
        }
    }

    override suspend fun getSessions(): ApiResponse =
        client.get {
            url(SESSIONS_URL)
        }.toApiResponse<SessionsDto>(json)

    override suspend fun confirmEmail(email: String): ApiResponse {
        val request = ConfirmEmailRequest(email)
        return client.post {
            url(CONFIRM_EMAIL_URL)
            setBody(request)
        }.toApiResponse<ConfirmEmailDto>(json)
    }

    override suspend fun updateUserInfo(
        email: String?,
        enabled: Boolean?,
        firstName: String?,
        lastName: String?,
    ): ApiResponse {
        val request = UpdateAccountRequest(email, enabled, firstName, lastName)
        return client.patch {
            url(UPDATE_ACCOUNT_URL)
            setBody(request)
        }.toApiResponse<UpdateAccountDto>(json)
    }

    override suspend fun closeOtherSessions(
        id: String,
    ): ApiResponse {
        val request = CloseSessionsRequest(id)
        return client.delete {
            url(SESSIONS_URL)
            setBody(request)
        }.toApiResponse<CloseSessionsDto>(json)
    }

    override suspend fun getProfileInfo(): ApiResponse {
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
    ): ApiResponse {
        val request = ProfileInfoRequest(
            contactInfo = ContactInfoRequest(
                email = email,
                phoneNumber = phoneNumber
            ),
            legalInfo = LegalInfoRequest(
                fullName = fullName,
                livingAddress = livingAddress,
                passport = PassportRequest(
                    issuer = passportIssuer,
                    passportNumber = passportNumber,
                    passportSeries = passportSeries
                )
            ).takeIf { fullName != null }
        )
        return client.post {
            url(PROFILE_INFO_URL)
            setBody(request)
        }.toApiResponse<Unit>(json)
    }
}
