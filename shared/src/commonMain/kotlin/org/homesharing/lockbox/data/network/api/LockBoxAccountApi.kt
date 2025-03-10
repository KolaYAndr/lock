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
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.dto.ConfirmEmailRequestDto
import org.homesharing.lockbox.data.network.dto.InitialTokenLoadResponseDto
import org.homesharing.lockbox.data.network.dto.PostImageResponseDto
import org.homesharing.lockbox.data.network.dto.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpRequestDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.dto.update_user.UpdateAccountRequestDto
import org.homesharing.lockbox.data.network.dto.update_user.UpdateAccountResponseDto
import org.homesharing.lockbox.data.network.token.model.TokenResponseDto
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
private const val CONFIRM_EMAIL_URL = "$BASE:8445/$ACCOUNTS/$SEND"
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
    ): SignUpResponseDto {
        return unsignedClient.post {
            url(SIGN_UP_URL)
            setBody(
                SignUpRequestDto(
                    email = username,
                    password = password,
                    repeatPassword = passwordRepeated,
                )
            )
        }.body<SignUpResponseDto>()
    }

    override suspend fun getImageLinkBySub(sub: String): String = "$AVATARS_URL/$sub"

    override suspend fun loadTokensInitially(
        username: String,
        password: String
    ): InitialTokenLoadResponseDto {
        val response = unsignedClient.submitForm(
            url = TOKEN_URL,
            formParameters = parameters {
                append(GRANT_TYPE, PASSWORD)
                append(CLIENT_ID, PUBLIC_CLIENT)
                append(USERNAME, username)
                append(PASSWORD, password)
                append(SCOPE, OFFLINE_ACCESS_SCOPE)
            }
        ).bodyAsText()

        return try {
            Json.decodeFromString<InitialTokenLoadResponseDto.InitialTokenLoadSuccessResponseDto>(response)
        } catch (e: SerializationException) {
            Json.decodeFromString<InitialTokenLoadResponseDto.InitialTokenLoadFailResponseDto>(response)
        }
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
        }.body<PostImageResponseDto>()

        unsignedClient.put {
            header(HttpHeaders.ContentType, ContentType.Image.PNG)
            url(response.body.link)
            setBody(data)
        }
    }

    override suspend fun getSessions(): SessionsResponseDto {
        return signedClient.get {
            url(SESSIONS_URL)
        }.body()
    }

    override suspend fun confirmEmail(body: ConfirmEmailRequestDto) {
        unsignedClient.post {
            url(CONFIRM_EMAIL_URL)
            setBody(body)
        }
    }

    override suspend fun updateUserInfo(body: UpdateAccountRequestDto): UpdateAccountResponseDto {
        return signedClient.patch {
            url(UPDATE_ACCOUNT_URL)
            setBody(body)
        }.body()
    }
}
