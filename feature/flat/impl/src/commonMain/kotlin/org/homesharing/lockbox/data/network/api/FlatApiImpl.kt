package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.create_flat.CreateFlatRequestDto
import org.homesharing.lockbox.data.network.dto.flat.FlatDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatListDto

// API endpoints
private const val FLATS = "flats"

// API arguments
private const val CURSOR = "cursor"
private const val LIMIT = "limit"
private const val INCLUDE = "include"
private const val INVITES = "invites"

// API url
private const val FLATS_URL = "$GO_BASE_ENDPOINT/$FLATS"

internal class FlatApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : FlatApi {
    override suspend fun getFlats(
        limit: Int,
        cursor: String?
    ): ApiResponse<FlatListDto> {
        return client.get {
            parameter(CURSOR, cursor)
            parameter(LIMIT, limit)
            url(FLATS_URL)
        }.toApiResponse<FlatListDto>(json)
    }

    override suspend fun getFlat(
        id: String,
        includeInvites: Boolean
    ): ApiResponse<FlatDto> {
        return client.get {
            if (includeInvites) {
                parameter(INCLUDE, INVITES)
            }
            url("$FLATS_URL/$id")
        }.toApiResponse<FlatDto>(json)
    }

    override suspend fun createFlat(request: CreateFlatRequestDto): ApiResponse<FlatDto> {
        return client.post {
            url(FLATS_URL)
            setBody(request)
        }.toApiResponse<FlatDto>(json)
    }

    override suspend fun updateFlat(id: String, request: CreateFlatRequestDto): ApiResponse<FlatDto> {
        return client.patch {
            url("$FLATS_URL/$id")
            setBody(request)
        }.toApiResponse<FlatDto>(json)
    }
}
