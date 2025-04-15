package org.homesharing.lockbox.data.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val CLIENT_TIMEOUT = 5_000L
private const val BASE_ENDPOINT = "https://api.lockboxes.ru"
private const val BEARER = "Bearer"

internal expect val networkEngine: HttpClientEngine
internal expect val myLogger: Logger

internal fun createNetworkClient(engine: HttpClientEngine, mLogger: Logger = myLogger): HttpClient {
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

internal fun bearerToken(token: String) = "$BEARER $token"
