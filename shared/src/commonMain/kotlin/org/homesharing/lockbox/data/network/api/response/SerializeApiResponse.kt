package org.homesharing.lockbox.data.network.api.response

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

private inline fun <reified T> String.toApiResponse(json: Json): ApiResponse = try {
    val parsed = json.decodeFromString<T>(this)
    ApiResponse.Success(parsed)
} catch (_: SerializationException) {
    json.decodeFromString<ApiResponse.Failure>(this)
}

internal suspend inline fun <reified T> HttpResponse.toApiResponse(json: Json): ApiResponse =
    this.bodyAsText().toApiResponse<T>(json)
