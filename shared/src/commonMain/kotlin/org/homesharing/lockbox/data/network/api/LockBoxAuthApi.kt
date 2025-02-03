package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.domain.api.AuthApi
import org.homesharing.lockbox.domain.model.InitialTokenLoadResponse
import org.homesharing.lockbox.domain.model.SignUpRequest
import org.homesharing.lockbox.domain.model.SignUpResponse

private const val ACCOUNTS = "accounts"
private const val SIGN_UP = "signup"

private const val PASSWORD = "password"
private const val SCOPE = "openid offline_access"
private const val CLIENT_ID = "public-client"

private const val TOKEN_INITIAL_URL =
    "https://api.lockboxes.ru:8443/realms/spring-app-realm/protocol/openid-connect/token"
private const val SIGN_UP_URL = "https://api.lockboxes.ru:8445/accounts/signup"

class LockBoxAuthApi(private val client: HttpClient) : AuthApi {
    override suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String
    ): SignUpResponse {
        return client.post {
            url(SIGN_UP_URL)
            setBody(
                SignUpRequest(
                    email = username,
                    password = password,
                    repeatPassword = passwordRepeated,
//                    username = username
                )
            )
        }.body()
    }

    override suspend fun loadTokensInitially(
        username: String,
        password: String
    ): InitialTokenLoadResponse {
        val response = client.submitForm(
            url = TOKEN_INITIAL_URL,
            formParameters = parameters {
                append("grant_type", PASSWORD)
                append("client_id", CLIENT_ID)
                append("username", username)
                append("password", password)
                append("scope", SCOPE)
            }
        ).bodyAsText()

        return try {
            Json.decodeFromString<InitialTokenLoadResponse.InitialTokenLoadSuccessResponse>(response)
        } catch (e: SerializationException) {
            Json.decodeFromString<InitialTokenLoadResponse.InitialTokenLoadFailResponse>(response)
        }
    }
}