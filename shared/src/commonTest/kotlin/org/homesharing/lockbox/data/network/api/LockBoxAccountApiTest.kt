package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsResponseDto
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailResponseDto
import org.homesharing.lockbox.data.network.dto.reset_password.ResetPasswordResponseDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@Suppress("UNCHECKED_CAST")
class LockBoxAccountApiTest {
    private lateinit var api: LockBoxAccountApi

    @BeforeTest
    fun setUp() {
        api = LockBoxAccountApi(mockAccountClient, json)
    }

    @Test
    fun `updateUserInfo returns correct domain model when response is successful`() = runBlocking {
        val username = "aboba@mail.ru"
        val result = api.signUp(
            username = username,
            password = "aboba",
            passwordRepeated = "aboba",
        ) as ApiResponse.Success<SignUpResponseDto>

        assertEquals(username, result.data.user.username)
        assertFalse(result.data.user.emailVerified)
        assertTrue(result.data.user.enabled)
        assertNull(result.data.user.firstName)
        assertNull(result.data.user.lastName)
        assertEquals(username, result.data.user.username)
    }

    @Test
    fun `loadTokensInitially returns valid tokens on successful response`() = runBlocking {
        val username = "test-user"
        val password = "test-password"

        val result = api.loadTokensInitially(username, password) as AuthResponse.Success

        assertEquals("test-access-token", result.accessToken)
        assertEquals("test-refresh-token", result.refreshToken)
        assertEquals(3600, result.expiresIn)
        assertEquals(18000, result.refreshExpiresIn)
        assertEquals("Bearer", result.tokenType)
    }

    @Test
    fun `getSessions returns sessions and credentials when authorized`() = runBlocking {
        val accessToken = "valid-token"

        val result = api.getSessions(accessToken) as ApiResponse.Success<SessionsResponseDto>

        val sessions = result.data.sessions
        val credentials = result.data.credentials

        assertEquals(1, sessions.size)
        assertEquals(1, credentials.size)

        with(sessions.first()) {
            assertEquals("0229bf26-8593-4a6b-9713-5ce729bfcf5a", id)
            assertEquals("aboba@mail.ru", username)
            assertEquals("97b9279c-7741-4017-bb62-a3d21431e9b3", userId)
            assertEquals("185.87.148.204", ipAddress)
            assertEquals(1749588070000, start)
            assertEquals(1749588077000, lastAccess)
            assertFalse(rememberMe)
            assertEquals(mapOf("5b508dfc-989b-42e5-8531-7f8cd450f619" to "public-client"), clients)
            assertFalse(transientUser)
        }

        with(credentials.first()) {
            assertEquals(1744909250836, createdDate)
            assertEquals("password", type)
            assertNull(userLabel)
        }
    }

    @Test
    fun `closeOtherSessions returns success when authorized with valid body`() = runBlocking {
        val accessToken = "valid-token"
        val sessionId = "test-session-id"

        val result = api.closeOtherSessions(accessToken, sessionId) as ApiResponse.Success<CloseSessionsResponseDto>

        assertEquals("session:deleted", result.data.message)
    }

    @Test
    fun `confirmEmail returns success response for valid email`() = runBlocking {
        val testEmail = "test@example.com"

        val result = api.confirmEmail(testEmail) as ApiResponse.Success<ConfirmEmailResponseDto>

        assertEquals("Sent", result.data.response)
    }

    @Test
    fun `updateUserInfo returns updated user data on success`() = runBlocking {
        val accessToken = "valid-token"

        val result = api.updateUserInfo(
            accessToken = accessToken,
            email = "studid@fuck.com",
            enabled = true,
            firstName = "Stupid",
            lastName = "Aboba"
        ) as ApiResponse.Success<UpdateAccountResponseDto>

        with(result.data) {
            assertEquals("97b9279c-7741-4017-bb62-a3d21431e9b3", id)
            assertEquals("studid@fuck.com", username)
            assertEquals("studid@fuck.com", email)
            assertEquals("Stupid", firstName)
            assertEquals("Aboba", lastName)
            assertFalse(emailVerified)
            assertTrue(enabled)
        }
    }

    @Test
    fun `sendLinkToChangePassword returns success message when authorized`() = runBlocking {
        val accessToken = "valid-token"

        val result = api.sendLinkToChangePassword(accessToken) as ApiResponse.Success<ResetPasswordResponseDto>

        assertEquals("user.message:password_reset_email_sent", result.data.message)
    }

    companion object {
        private val headers = headersOf(
            name = HttpHeaders.ContentType,
            value = "${ContentType.Application.Json.contentType}/${ContentType.Application.Json.contentSubtype}"
        )

        private val urlToContent = mapOf(
            SIGN_UP_URL to """
                        {
                            "body": {
                                "user": {
                                "id": "87d644ba-22a9-406f-8d85-049b04309e31",
                                "username": "aboba@mail.ru",
                                "email": "aboba@mail.ru",
                                "first_name": null,
                                "last_name": null,
                                "email_verified": false,
                                "enabled": true
                                }
                            },
                            "error": false,
                            "error_text": null
                        }
                        """,
            TOKEN_URL to """
                {
                    "access_token": "test-access-token",
                    "expires_in": 3600,
                    "refresh_token": "test-refresh-token",
                    "refresh_expires_in": 18000,
                    "token_type": "Bearer"
                }
                """,

            SESSIONS_URL to """
            {
                "body": {
                    "sessions": [
                        {
                            "id": "0229bf26-8593-4a6b-9713-5ce729bfcf5a",
                            "username": "aboba@mail.ru",
                            "user_id": "97b9279c-7741-4017-bb62-a3d21431e9b3",
                            "ip_address": "185.87.148.204",
                            "start": 1749588070000,
                            "last_access": 1749588077000,
                            "remember_me": false,
                            "clients": {
                                "5b508dfc-989b-42e5-8531-7f8cd450f619": "public-client"
                            },
                            "transient_user": false
                        }
                    ],
                    "credentials": [
                        {
                            "created_date": 1744909250836,
                            "type": "password",
                            "user_label": null
                        }
                    ]
                },
                "error": false,
                "error_text": null
            }
        """,

            "$SESSIONS_URL/${HttpMethod.Delete.value}" to """
            {
                "body": "session:deleted",
                "error": false,
                "error_text": null
                }
            """,

            CONFIRM_EMAIL_URL to """
            {
                "body": "Sent",
                "error": false,
                "error_text": null
            }
            """,

            UPDATE_ACCOUNT_URL to """
            {
                "body": {
                    "id": "97b9279c-7741-4017-bb62-a3d21431e9b3",
                    "username": "studid@fuck.com",
                    "email": "studid@fuck.com",
                    "first_name": "Stupid",
                    "last_name": "Aboba",
                    "email_verified": false,
                    "enabled": true
                },
                "error": false,
                "error_text": null
            }
            """,

            RESET_PASSWORD_URL to """
            {
                "body": "user.message:password_reset_email_sent",
                "error": false,
                "error_text": null
            }
        """
        )

        private val mockRequestHandler: MockRequestHandler = { request ->
            val url = request.url.toString()
            val contentKey = when {
                url == SESSIONS_URL && request.method == HttpMethod.Delete -> "$SESSIONS_URL/${HttpMethod.Delete.value}"
                else -> url
            }

            val content = urlToContent.getOrElse(contentKey) {
                error("Unhandled request: ${request.method} ${request.url}")
            }

            respond(content = content, headers = headers)
        }

        private val mockEngine = MockEngine(mockRequestHandler)

        private val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }

        val mockAccountClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(
                    json = json,
                )
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}
