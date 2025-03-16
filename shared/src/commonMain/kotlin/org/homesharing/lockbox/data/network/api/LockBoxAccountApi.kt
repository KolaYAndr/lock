package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
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
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailRequestDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailResponseDto
import org.homesharing.lockbox.data.network.dto.post_image.PostImageResponseDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpRequestDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.dto.token_response.TokenResponseDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountRequestDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto
import org.homesharing.lockbox.data.network.token.storage.REFRESH_TOKEN_NAME
import org.homesharing.lockbox.domain.api.AccountApi

private const val REALM = "realms/lockbox"
private const val PROTOCOL = "protocol"
private const val OPENID_CONNECT_TOKEN = "openid-connect/token"
private const val ACCOUNTS = "accounts"
private const val AVATARS = "avatars"
private const val SIGN_UP = "signup"
private const val SESSIONS = "sessions"
private const val UPLOAD = "upload"
private const val MAIL = "mail"
private const val SEND = "send"
private const val ME = "me"

private const val PASSWORD = "password"
private const val USERNAME = "username"
private const val OFFLINE_ACCESS_SCOPE = "openid offline_access"
private const val PUBLIC_CLIENT = "public-client"
private const val CLIENT_ID = "client_id"
private const val GRANT_TYPE = "grant_type"
private const val SCOPE = "scope"
private const val REFRESH_TOKEN = "refresh_token"

private const val BASE = "https://api.lockboxes.ru"

//TODO потом, когда переедем на безпортовые, надо убрать их из урлов
private const val TOKEN_URL =
    "$BASE:8443/$REALM/$PROTOCOL/$OPENID_CONNECT_TOKEN"
private const val SIGN_UP_URL = "$BASE:8445/$ACCOUNTS/$SIGN_UP"
private const val SESSIONS_URL = "$BASE:8445/$ACCOUNTS/$SESSIONS"
private const val AVATARS_POST_URL = "$BASE:8445/$AVATARS/$UPLOAD"
private const val CONFIRM_EMAIL_URL = "$BASE:8445/$ACCOUNTS/$MAIL/$SEND"
private const val UPDATE_ACCOUNT_URL = "$BASE:8445/$ACCOUNTS/$ME"
private const val AVATARS_URL = "$BASE:9000/$AVATARS"

class LockBoxAccountApi(
    private val unsignedClient: HttpClient,
    private val signedClient: HttpClient
) : AccountApi {

    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): ApiResponse<SignUpResponseDto> {
        val request = SignUpRequestDto(
            email = username,
            password = password,
            repeatPassword = passwordRepeated,
        )
        return unsignedClient.post {
            url(SIGN_UP_URL)
            setBody(request)
        }.body()
    }

    override suspend fun getImageLinkBySub(sub: String): String = "$AVATARS_URL/$sub"

    override suspend fun loadTokensInitially(
        username: String,
        password: String
    ): TokenResponseDto {
        return unsignedClient.submitForm(
            url = TOKEN_URL,
            formParameters = parameters {
                append(GRANT_TYPE, PASSWORD)
                append(CLIENT_ID, PUBLIC_CLIENT)
                append(USERNAME, username)
                append(PASSWORD, password)
                append(SCOPE, OFFLINE_ACCESS_SCOPE)
            }
        ).body()
    }

    override suspend fun refreshTokens(refreshToken: String): TokenResponseDto {
        return signedClient.submitForm(
            url = TOKEN_URL,
            formParameters = parameters {
                append(GRANT_TYPE, REFRESH_TOKEN)
                append(CLIENT_ID, PUBLIC_CLIENT)
                append(REFRESH_TOKEN_NAME, refreshToken)
            }
        ).body()
    }

    override suspend fun sendImage(data: ByteArray) {
        val response = signedClient.post {
            url(AVATARS_POST_URL)
        }.body<ApiResponse<PostImageResponseDto>>()

        unsignedClient.put {
            header(HttpHeaders.ContentType, ContentType.Image.PNG)
            url(response.body!!.link)
            setBody(data)
        }
    }

    override suspend fun getSessions(): ApiResponse<SessionsResponseDto> {
        return signedClient.get {
            url(SESSIONS_URL)
        }.body()
    }

    override suspend fun confirmEmail(email: String): ApiResponse<ConfirmEmailResponseDto> {
        val request = ConfirmEmailRequestDto(email)
        return unsignedClient.post {
            url(CONFIRM_EMAIL_URL)
            setBody(request)
        }.body()
    }

    override suspend fun updateUserInfo(
        email: String?,
        enabled: Boolean?,
        firstName: String?,
        lastName: String?
    ): ApiResponse<UpdateAccountResponseDto> {
        val request = UpdateAccountRequestDto(email, enabled, firstName, lastName)
        return signedClient.patch {
            url(UPDATE_ACCOUNT_URL)
            setBody(request)
        }.body()
    }
}
