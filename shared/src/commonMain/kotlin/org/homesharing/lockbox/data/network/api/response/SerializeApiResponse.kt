package org.homesharing.lockbox.data.network.api.response

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

//internal suspend inline fun <reified T> HttpResponse.toApiResponse(json: Json): ApiResponse<T> {
//    val responseBody = bodyAsText()
//
//    return if (status.value in 200..299) {
//        ApiResponse.Success(json.decodeFromString<T>(responseBody))
//    } else {
//        json.decodeFromString<ApiResponse.Failure>(responseBody)
//    }
//}

internal suspend inline fun <reified T> HttpResponse.toApiResponse(json: Json): ApiResponse<T> {
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
            json.decodeFromString<ApiResponse.Failure>(responseBody)
        }
    }
}
