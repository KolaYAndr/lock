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
import io.ktor.client.request.forms.submitForm
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.api.response.toAuthResponse
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.REFRESH_TOKEN_NAME
import org.homesharing.lockbox.data.network.token.storage.TokenStorage

private const val API = "api"
private const val V1 = "v1"

internal const val KEYCLOACK_BASE_ENDPOINT = "https://api.lockboxes.ru"
internal const val KOTLIN_BASE_ENDPOINT = "https://kotlin.ingress.lockboxes.ru/$API/$V1"
internal const val GO_BASE_ENDPOINT = "https://go.ingress.lockboxes.ru/$API"

private const val REALM = "realms/lockbox"
private const val PROTOCOL = "protocol"
private const val OPENID_CONNECT_TOKEN = "openid-connect/token"
internal const val TOKEN_URL =
    "$KEYCLOACK_BASE_ENDPOINT:8443/$REALM/$PROTOCOL/$OPENID_CONNECT_TOKEN"

private const val CLIENT_TIMEOUT = 5_000L
private const val CLIENT_ID = "client_id"
private const val WEB_FRONT = "web-front"
private const val GRANT_TYPE = "grant_type"
private const val REFRESH_TOKEN = "refresh_token"

internal expect val networkEngine: HttpClientEngine
internal expect val myLogger: Logger

internal fun createNetworkClient(
    engine: HttpClientEngine,
    json: Json,
    tokenStorage: TokenStorage,
    refreshManager: RefreshManager,
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
                    val tokenResponse = client.submitForm(
                        url = TOKEN_URL,
                        formParameters = parameters {
                            append(GRANT_TYPE, REFRESH_TOKEN)
                            append(CLIENT_ID, WEB_FRONT)
                            append(REFRESH_TOKEN_NAME, oldTokens?.refreshToken.orEmpty())
                        }
                    ).toAuthResponse(json)

                    if (tokenResponse is AuthResponse.Success) {
                        tokenStorage.saveTokens(
                            tokenResponse.accessToken,
                            tokenResponse.refreshToken
                        )
                        tokenStorage.saveNextRefreshTime(tokenResponse.expiresIn + refreshManager.currentTime)

                        BearerTokens(
                            accessToken = tokenResponse.accessToken,
                            refreshToken = tokenResponse.refreshToken
                        )
                    } else {
                        tokenStorage.clearInfo()
                        null
                    }
                }
            }
        }

        defaultRequest {
//            url { takeFrom(BASE_ENDPOINT) }
            contentType(ContentType.Application.Json)
        }
    }
}