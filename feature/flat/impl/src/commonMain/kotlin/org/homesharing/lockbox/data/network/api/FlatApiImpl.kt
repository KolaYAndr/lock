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
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatDetailDto
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatIncludeDto
import org.homesharing.lockbox.data.network.dto.flat_detail.apiValue
import org.homesharing.lockbox.data.network.dto.flat_list.FlatStatusFilterDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatSummaryListDto
import org.homesharing.lockbox.data.network.dto.flat_list.apiValue
import org.homesharing.lockbox.data.network.dto.flat_photo.FlatPhotoListDto

// API endpoints
private const val FLATS = "flats"
private const val ARCHIVE = "archive"
private const val RESTORE = "restore"
private const val PHOTOS = "photos"

// API arguments
private const val CURSOR = "cursor"
private const val LIMIT = "limit"
private const val STATUS = "status"
private const val INCLUDE = "include"

// API url
private const val FLATS_URL = "$GO_BASE_ENDPOINT/$FLATS"

internal class FlatApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : FlatApi {
    override suspend fun getFlats(
        limit: Int,
        cursor: String?,
        status: FlatStatusFilterDto,
    ): ApiResponse<FlatSummaryListDto> {
        return client.get {
            url(FLATS_URL)
            parameter(CURSOR, cursor)
            parameter(LIMIT, limit)
            parameter(STATUS, status.apiValue)
        }.toApiResponse<FlatSummaryListDto>(json)
    }

    override suspend fun getFlat(
        id: String,
        include: FlatIncludeDto?,
    ): ApiResponse<FlatDetailDto> {
        return client.get {
            url("$FLATS_URL/$id")
            include?.let { parameter(INCLUDE, it.apiValue) }
        }.toApiResponse<FlatDetailDto>(json)
    }

    override suspend fun getFlatPhotos(id: String): ApiResponse<FlatPhotoListDto> {
        return client.get {
            url("$FLATS_URL/$id/$PHOTOS")
        }.toApiResponse<FlatPhotoListDto>(json)
    }

    override suspend fun createFlat(request: CreateFlatRequestDto): ApiResponse<FlatDetailDto> {
        return client.post {
            url(FLATS_URL)
            setBody(request)
        }.toApiResponse<FlatDetailDto>(json)
    }

    override suspend fun updateFlat(id: String, request: CreateFlatRequestDto): ApiResponse<FlatDetailDto> {
        return client.patch {
            url("$FLATS_URL/$id")
            setBody(request)
        }.toApiResponse<FlatDetailDto>(json)
    }

    override suspend fun archiveFlat(id: String): ApiResponse<FlatDetailDto> {
        return client.post {
            url("$FLATS_URL/$id/$ARCHIVE")
        }.toApiResponse<FlatDetailDto>(json)
    }

    override suspend fun restoreFlat(id: String): ApiResponse<FlatDetailDto> {
        return client.post {
            url("$FLATS_URL/$id/$RESTORE")
        }.toApiResponse<FlatDetailDto>(json)
    }
}
