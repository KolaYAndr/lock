package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.model.AvatarResponse
import org.homesharing.lockbox.data.network.model.InitialTokenLoadResponse
import org.homesharing.lockbox.data.network.model.PostImageResponse
import org.homesharing.lockbox.data.network.model.SignUpRequest
import org.homesharing.lockbox.data.network.model.SignUpResponse
import org.homesharing.lockbox.domain.api.AccountApi

private const val ACCOUNTS = "accounts"
private const val SIGN_UP = "signup"

private const val PASSWORD = "password"
private const val USERNAME = "username"
private const val OFFLINE_ACCESS_SCOPE = "openid offline_access"
private const val PUBLIC_CLIENT = "public-client"
private const val CLIENT_ID = "client_id"
private const val GRANT_TYPE = "grant_type"
private const val SCOPE = "scope"

private const val TOKEN_INITIAL_URL =
    "https://api.lockboxes.ru:8443/realms/lockbox/protocol/openid-connect/token"
private const val SIGN_UP_URL = "https://api.lockboxes.ru:8445/accounts/signup"
private const val AVATARS_URL = "https://api.lockboxes.ru:8445/avatars/get"
private const val AVATARS_POST_URL = "https://api.lockboxes.ru:8445/avatars/upload"
private const val SUB_PARAMETER = "sub"

class LockBoxAccountApi(
    private val unsignedClient: HttpClient,
    private val signedClient: HttpClient
) : AccountApi {
    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): SignUpResponse {
        return unsignedClient.post {
            url(SIGN_UP_URL)
            setBody(
                SignUpRequest(
                    email = username,
                    password = password,
                    repeatPassword = passwordRepeated,
                )
            )
        }.body<SignUpResponse>()
    }

    override suspend fun loadTokensInitially(
        username: String,
        password: String
    ): InitialTokenLoadResponse {
        val response = unsignedClient.submitForm(
            url = TOKEN_INITIAL_URL,
            formParameters = parameters {
                append(GRANT_TYPE, PASSWORD)
                append(CLIENT_ID, PUBLIC_CLIENT)
                append(USERNAME, username)
                append(PASSWORD, password)
                append(SCOPE, OFFLINE_ACCESS_SCOPE)
            }
        ).bodyAsText()

        val json = Json { ignoreUnknownKeys = true }

        return try {
            json.decodeFromString<InitialTokenLoadResponse.InitialTokenLoadSuccessResponse>(response)
        } catch (e: SerializationException) {
            json.decodeFromString<InitialTokenLoadResponse.InitialTokenLoadFailResponse>(response)
        }
    }

    override suspend fun getAvatarLink(sub: String): AvatarResponse {
        return unsignedClient.get {
            url(AVATARS_URL)
            parameter(SUB_PARAMETER, sub)
        }.body()
    }

    override suspend fun sendImage(data: ByteArray) {
        val response = signedClient.post {
            url(AVATARS_POST_URL)
        }.body<PostImageResponse>()

        unsignedClient.put {
            url(response.body.link)
            setBody(data)
        }
    }
}
