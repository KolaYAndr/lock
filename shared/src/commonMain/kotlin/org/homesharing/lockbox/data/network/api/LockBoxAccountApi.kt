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
import org.homesharing.lockbox.data.network.client.BASE_ENDPOINT
import org.homesharing.lockbox.data.network.client.bearerToken
import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsRequestDto
import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsResponseDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailRequestDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailResponseDto
import org.homesharing.lockbox.data.network.dto.post_image.PostImageResponseDto
import org.homesharing.lockbox.data.network.dto.reset_password.ResetPasswordResponseDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpRequestDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountRequestDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto
import org.homesharing.lockbox.data.network.token.storage.REFRESH_TOKEN_NAME
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
private const val RESET_PASSWORD = "reset-password"
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
private const val REFRESH_TOKEN = "refresh_token"

//TODO потом, когда переедем на безпортовые, надо убрать их из урлов
internal const val TOKEN_URL = "$BASE_ENDPOINT:8443/$REALM/$PROTOCOL/$OPENID_CONNECT_TOKEN"
internal const val SIGN_UP_URL = "$BASE_ENDPOINT:8445/$USERS/$SIGN_UP"
internal const val SESSIONS_URL = "$BASE_ENDPOINT:8445/$ACCOUNTS/$SESSIONS"
internal const val RESET_PASSWORD_URL = "$BASE_ENDPOINT:8445/$ACCOUNTS/$RESET_PASSWORD"
internal const val AVATARS_POST_URL = "$BASE_ENDPOINT:8445/$USER/$AVATAR/$UPLOAD"
internal const val CONFIRM_EMAIL_URL = "$BASE_ENDPOINT:8445/$ACCOUNTS/$MAIL/$SEND"
internal const val UPDATE_ACCOUNT_URL = "$BASE_ENDPOINT:8445/$ACCOUNTS/$ME"
internal const val AVATARS_URL = "$BASE_ENDPOINT:9000/$USER/$AVATAR"

internal class LockBoxAccountApi(
    private val client: HttpClient,
    private val json: Json,
) : AccountApi {

    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String,
    ): ApiResponse {
        val request = SignUpRequestDto(
            email = username,
            password = password,
            repeatPassword = passwordRepeated,
        )
        return client.post {
            url(SIGN_UP_URL)
            setBody(request)
        }.toApiResponse<SignUpResponseDto>(json)
    }

    override suspend fun getImageLink(sub: String): String = "$AVATARS_URL/$sub"

    override suspend fun loadTokensInitially(
        username: String,
        password: String,
    ): AuthResponse =
        client.submitForm(
            url = TOKEN_URL,
            formParameters = parameters {
                append(GRANT_TYPE, PASSWORD)
                append(CLIENT_ID, WEB_FRONT)
                append(USERNAME, username)
                append(PASSWORD, password)
                append(SCOPE, OFFLINE_ACCESS_SCOPE)
            }
        ).toAuthResponse(json)

    override suspend fun refreshTokens(
        accessToken: String,
        refreshToken: String,
    ): AuthResponse =
        client.submitForm(
            url = TOKEN_URL,
            formParameters = parameters {
                append(GRANT_TYPE, REFRESH_TOKEN)
                append(CLIENT_ID, WEB_FRONT)
                append(REFRESH_TOKEN_NAME, refreshToken)
            },
            block = {
                header(HttpHeaders.Authorization, bearerToken(accessToken))
            }
        ).toAuthResponse(json)

    override suspend fun sendImage(accessToken: String, data: ByteArray): ApiResponse {
        val response = client.post {
            url(AVATARS_POST_URL)
            header(HttpHeaders.Authorization, bearerToken(accessToken))
        }.toApiResponse<PostImageResponseDto>(json)

        return if (response is ApiResponse.Success<*>) {
            val link = (response.data as PostImageResponseDto).link
            client.put {
                url(link)
                header(HttpHeaders.ContentType, ContentType.Image.PNG)
                setBody(data)
            }.toApiResponse<Unit>(json)
        } else {
            val result = response as ApiResponse.Failure
            ApiResponse.Failure(
                errorCode = result.errorCode,
                message = result.message
            )
        }
    }

    override suspend fun getSessions(accessToken: String): ApiResponse =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url(SESSIONS_URL)
        }.toApiResponse<SessionsResponseDto>(json)

    override suspend fun confirmEmail(email: String): ApiResponse {
        val request = ConfirmEmailRequestDto(email)
        return client.post {
            url(CONFIRM_EMAIL_URL)
            setBody(request)
        }.toApiResponse<ConfirmEmailResponseDto>(json)
    }

    override suspend fun updateUserInfo(
        accessToken: String,
        email: String?,
        enabled: Boolean?,
        firstName: String?,
        lastName: String?,
    ): ApiResponse {
        val request = UpdateAccountRequestDto(email, enabled, firstName, lastName)
        return client.patch {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url(UPDATE_ACCOUNT_URL)
            setBody(request)
        }.toApiResponse<UpdateAccountResponseDto>(json)
    }

    override suspend fun closeOtherSessions(
        accessToken: String,
        id: String,
    ): ApiResponse {
        val request = CloseSessionsRequestDto(id)
        return client.delete {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url(SESSIONS_URL)
            setBody(request)
        }.toApiResponse<CloseSessionsResponseDto>(json)
    }

    override suspend fun sendLinkToChangePassword(accessToken: String): ApiResponse =
        client.post {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url(RESET_PASSWORD_URL)
        }.toApiResponse<ResetPasswordResponseDto>(json)
}
