package org.homesharing.lockbox.data.network.api.response

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

private fun String.toAuthResponse(json: Json): AuthResponse = try {
    json.decodeFromString(AuthResponse.Success.serializer(), this)
} catch (_: SerializationException) {
    json.decodeFromString(AuthResponse.Error.serializer(), this)
}

internal suspend fun HttpResponse.toAuthResponse(json: Json): AuthResponse =
    this.bodyAsText().toAuthResponse(json)
