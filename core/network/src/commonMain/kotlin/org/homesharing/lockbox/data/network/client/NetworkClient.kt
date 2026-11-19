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
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.dto.refresh.RefreshTokenRequest
import org.homesharing.lockbox.data.network.dto.token.TokenResponseDto
import org.homesharing.lockbox.data.network.token.TokenStorage

private const val API = "api"
private const val V1 = "v1"

const val KEYCLOACK_BASE_ENDPOINT = "https://api.lockboxes.ru"
const val KOTLIN_BASE_ENDPOINT = "https://kotlin.ingress.lockboxes.ru/$API/$V1"
const val GO_BASE_ENDPOINT = "https://go.ingress.lockboxes.ru/$API"

private const val REALM = "realms/lockbox"
private const val PROTOCOL = "protocol"
private const val OPENID_CONNECT_TOKEN = "openid-connect/token"
const val TOKEN_URL =
    "$KEYCLOACK_BASE_ENDPOINT:8443/$REALM/$PROTOCOL/$OPENID_CONNECT_TOKEN"

private const val CLIENT_TIMEOUT = 5_000L
private const val AUTH = "auth"
private const val REFRESH = "refresh"
private const val REFRESH_URL = "$GO_BASE_ENDPOINT/$AUTH/$REFRESH"

expect val networkEngine: HttpClientEngine
expect val myLogger: Logger

internal suspend fun HttpClient.refreshToken(
    refreshToken: String,
    json: Json,
): ApiResponse<TokenResponseDto> {
    return post {
        url(REFRESH_URL)
        setBody(RefreshTokenRequest(refreshToken))
    }.toApiResponse<TokenResponseDto>(json)
}

fun createNetworkClient(
    engine: HttpClientEngine,
    json: Json,
    tokenStorage: TokenStorage,
    mLogger: Logger = myLogger,
): HttpClient {
    return HttpClient(engine) {
        install(HttpTimeout) {
            socketTimeoutMillis = CLIENT_TIMEOUT
            requestTimeoutMillis = CLIENT_TIMEOUT
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = mLogger
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

                    val response = runCatching {
                        client.refreshToken(currentRefreshToken, json)
                    }.getOrNull()

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
                        else -> {
                            tokenStorage.clearInfo()
                            null
                        }
                    }
                }
            }
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
}
