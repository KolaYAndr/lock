package org.homesharing.lockbox.data.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.dto.refresh.RefreshTokenRequest
import org.homesharing.lockbox.data.network.dto.token.TokenResponseDto
import org.homesharing.lockbox.data.network.token.TokenStorage

private const val API = "api"
const val GO_BASE_ENDPOINT = "https://go.ingress.lockboxes.ru/$API"

private const val CLIENT_TIMEOUT = 15_000L
private const val AUTH = "auth"
private const val REFRESH = "refresh"
private const val REFRESH_URL = "$AUTH/$REFRESH"

expect val networkEngine: HttpClientEngine
expect val myLogger: Logger

fun createNetworkClient(
    engine: HttpClientEngine,
    json: Json,
    tokenStorage: TokenStorage,
): HttpClient {
    return HttpClient(engine) {
        install(HttpTimeout) {
            connectTimeoutMillis = CLIENT_TIMEOUT
            socketTimeoutMillis = CLIENT_TIMEOUT
            requestTimeoutMillis = CLIENT_TIMEOUT
        }

        install(Logging) {
            level = LogLevel.HEADERS
            logger = myLogger
            sanitizeHeader { header ->
                header.equals(HttpHeaders.Authorization, ignoreCase = true) ||
                    header.equals(HttpHeaders.Cookie, ignoreCase = true) ||
                    header.equals(HttpHeaders.SetCookie, ignoreCase = true)
            }
        }

        install(ContentNegotiation) {
            json(
                json = json,
                contentType = ContentType.Application.Json
            )
        }

        install(Auth) {
            bearer {
                cacheTokens = false
                sendWithoutRequest { request ->
                    val requestUrl = request.url.buildString()
                    requestUrl.startsWith(GO_BASE_ENDPOINT)
                }
                loadTokens {
                    val accessToken = tokenStorage.getAccessToken()
                    val refreshToken = tokenStorage.getRefreshToken()
                    if (accessToken.isBlank() || refreshToken.isBlank()) return@loadTokens null
                    BearerTokens(accessToken, refreshToken)
                }

                refreshTokens {
                    val currentRefreshToken = oldTokens?.refreshToken
                        ?.takeIf { it.isNotBlank() }
                        ?: tokenStorage.getRefreshToken().takeIf { it.isNotBlank() }
                        ?: return@refreshTokens null

                    val response = try {
                        client.post {
                            url(REFRESH_URL)
                            markAsRefreshTokenRequest()
                            setBody(RefreshTokenRequest(currentRefreshToken))
                        }.toApiResponse<TokenResponseDto>(json)
                    } catch (e: CancellationException) {
                        throw e
                    } catch (_: Exception) {
                        return@refreshTokens null
                    }

                    when (response) {
                        is ApiResponse.Success -> {
                            tokenStorage.saveTokens(
                                response.data.accessToken,
                                response.data.refreshToken
                            )

                            BearerTokens(
                                accessToken = response.data.accessToken,
                                refreshToken = response.data.refreshToken
                            )
                        }

                        is ApiResponse.Failure -> {
                            tokenStorage.clearInfo()
                            null
                        }
                    }
                }
            }
        }

        defaultRequest {
            url("$GO_BASE_ENDPOINT/")
            contentType(ContentType.Application.Json)
        }
    }
}
