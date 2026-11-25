package org.homesharing.lockbox.data.network.api.response

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

suspend inline fun <reified T> HttpResponse.toApiResponse(json: Json): ApiResponse<T> {
    val responseBody = bodyAsText()

    return when {
        status.value in 200..299 -> {
            if (T::class == Unit::class && responseBody.isBlank()) {
                ApiResponse.Success(Unit as T)
            } else {
                ApiResponse.Success(json.decodeFromString<T>(responseBody))
            }
        }

        else -> {
            try {
                json.decodeFromString<ApiResponse.Failure>(responseBody)
            } catch (_: SerializationException) {
                ApiResponse.Failure(
                    errorCode = status.value.toString(),
                    message = responseBody.ifBlank { status.description },
                )
            }
        }
    }
}
