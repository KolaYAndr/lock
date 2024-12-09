package org.homesharing.lockbox.data.network.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.token.model.TokenInfo
import org.homesharing.lockbox.data.network.token.storage.TokenStorage

private const val CLIENT_TIMEOUT = 5_000L
private const val BASE_ENDPOINT = "api.lockboxes.ru"

private const val CLIENT_ID = "client_id"
private const val PUBLIC_CLIENT = "public_client"
private const val REFRESH_TOKEN = "refresh_token"
private const val GRANT_TYPE = "grant_type"

expect val networkEngine: HttpClientEngine

internal fun createNetworkClient(engine: HttpClientEngine, tokenStorage: TokenStorage): HttpClient {
    return HttpClient(engine) {
        install(HttpTimeout) {
            socketTimeoutMillis = CLIENT_TIMEOUT
            requestTimeoutMillis = CLIENT_TIMEOUT
        }
        install(Logging) {
            level = LogLevel.BODY
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }

        defaultRequest {
            url {
                takeFrom(BASE_ENDPOINT)
            }
        }

        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = tokenStorage.getAccessToken()
                        ?: throw Throwable(message = "No access token found")
                    val refresh = tokenStorage.getRefreshToken()
                    BearerTokens(accessToken = accessToken, refreshToken = refresh)
                }

                refreshTokens {
                    val tokenInfo: TokenInfo = client.submitForm(
                        url = "",
                        formParameters = parameters {
                            append(CLIENT_ID, PUBLIC_CLIENT)
                            append(REFRESH_TOKEN, oldTokens?.refreshToken ?: "")
                            append(GRANT_TYPE, REFRESH_TOKEN)
                        }
                    ) {
                        markAsRefreshTokenRequest()
                    }.body()
                    if (tokenStorage.getRefreshToken() == null) tokenStorage.saveRefreshToken(
                        tokenInfo.refreshToken
                    )
                    tokenStorage.saveAccessToken(tokenInfo.accessToken)

                    BearerTokens(tokenInfo.accessToken, tokenInfo.refreshToken)
                }
            }
        }
    }
}