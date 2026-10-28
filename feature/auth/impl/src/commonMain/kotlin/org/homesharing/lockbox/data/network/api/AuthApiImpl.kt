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
import org.homesharing.lockbox.data.network.client.KEYCLOACK_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.client.KOTLIN_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.client.TOKEN_URL
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailRequest
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpRequest

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

private const val SIGN_UP_URL = "$KOTLIN_BASE_ENDPOINT/$USERS/$SIGN_UP"
private const val CONFIRM_EMAIL_URL = "$KEYCLOACK_BASE_ENDPOINT:8445/$ACCOUNTS/$MAIL/$SEND"

internal class AuthApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : AuthApi {
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

    override suspend fun confirmEmail(email: String): ApiResponse<ConfirmEmailDto> {
        val request = ConfirmEmailRequest(email)
        return client.post {
            url(CONFIRM_EMAIL_URL)
            setBody(request)
        }.toApiResponse<ConfirmEmailDto>(json)
    }
}
