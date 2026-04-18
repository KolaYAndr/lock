package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.flat.FlatDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatListDto

private const val FLATS = "flats"

private const val CURSOR = "cursor"

private const val LIMIT = "limit"

internal class LockBoxDocumentsApi(
    private val client: HttpClient,
    private val json: Json,
) : DocumentsApi {
    override suspend fun getFlats(cursor: String, limit: Int): ApiResponse {
        return client.get {
            parameter(CURSOR, cursor)
            parameter(LIMIT, limit)
            url("$GO_BASE_ENDPOINT/$FLATS")
        }.toApiResponse<FlatListDto>(json)
    }

    override suspend fun createFlat(): ApiResponse {
        return client.post {
            url("$GO_BASE_ENDPOINT/$FLATS")
        }.toApiResponse<FlatDto>(json)
    }
}