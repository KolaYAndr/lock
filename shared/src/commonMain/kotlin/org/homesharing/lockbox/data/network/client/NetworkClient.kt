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
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.submitForm
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.parameters
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.token.model.TokenInfo
import org.homesharing.lockbox.data.network.token.storage.TokenStorage

private const val CLIENT_TIMEOUT = 5_000L
private const val BASE_ENDPOINT = "https://api.lockboxes.ru"
private const val REALM = "realms/lockbox"
private const val TOKEN_URL = "protocol/openid-connect/token"
private const val TOKEN_PORT = "8443" //TODO потом, когда переедем на безпортовые, надо убрать их из урлов

private const val CLIENT_ID = "client_id"
private const val PUBLIC_CLIENT = "public-client"
private const val REFRESH_TOKEN = "refresh_token"
private const val GRANT_TYPE = "grant_type"

expect val networkEngine: HttpClientEngine
expect val myLogger: Logger

internal fun createNetworkClient(engine: HttpClientEngine, mLogger: Logger = myLogger): HttpClient {
    return HttpClient(engine) {
        install(HttpTimeout) {
            socketTimeoutMillis = CLIENT_TIMEOUT
            requestTimeoutMillis = CLIENT_TIMEOUT
        }

        install(Logging) {
            level = LogLevel.BODY
            logger = mLogger
        }

        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                },
                contentType = ContentType.Application.Json
            )
        }

        defaultRequest {
            url { takeFrom(BASE_ENDPOINT) }
            contentType(ContentType.Application.Json)
        }
    }
}

internal fun HttpClient.installAuth(tokenStorage: TokenStorage) = this.config {
    defaultRequest {
        headers {
            bearerAuth(runBlocking { tokenStorage.getAccessToken() })
        }
    }

    install(Auth) {
        bearer {
            loadTokens {
                val accessToken = tokenStorage.getAccessToken()
                val refresh = tokenStorage.getRefreshToken()
                BearerTokens(accessToken = accessToken, refreshToken = refresh)
            }

            refreshTokens {
                val tokenInfo: TokenInfo = client.submitForm(
                    url = "$BASE_ENDPOINT:$TOKEN_PORT/$REALM/$TOKEN_URL",
                    formParameters = parameters {
                        append(CLIENT_ID, PUBLIC_CLIENT)
                        append(REFRESH_TOKEN, runBlocking { tokenStorage.getRefreshToken() } )
                        append(GRANT_TYPE, REFRESH_TOKEN)
                    }
                ) {
                    markAsRefreshTokenRequest()
                }.body()

                tokenStorage.apply {
                    saveAccessToken(tokenInfo.accessToken)
                    saveRefreshToken(tokenInfo.refreshToken)
                }

                BearerTokens(tokenInfo.accessToken, tokenInfo.refreshToken)
            }
        }
    }
}
