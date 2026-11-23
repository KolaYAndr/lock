package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.create_flat.CreateFlatRequestDto
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatDetailDto
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatIncludeDto
import org.homesharing.lockbox.data.network.dto.flat_detail.apiValue
import org.homesharing.lockbox.data.network.dto.flat_document.FlatDocumentListDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatStatusFilterDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatSummaryListDto
import org.homesharing.lockbox.data.network.dto.flat_list.apiValue
import org.homesharing.lockbox.data.network.dto.flat_photo.FlatPhotoListDto
import org.homesharing.lockbox.data.network.dto.flat_photo.ReorderPhotosRequestDto

// API endpoints
private const val FLATS = "flats"
private const val ARCHIVE = "archive"
private const val RESTORE = "restore"
private const val PHOTOS = "photos"
private const val ORDER = "order"
private const val DOCUMENTS = "documents"

// API arguments
private const val CURSOR = "cursor"
private const val LIMIT = "limit"
private const val STATUS = "status"
private const val INCLUDE = "include"
private const val PHOTO = "photo"

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

    override suspend fun uploadFlatPhoto(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
    ): ApiResponse<FlatPhotoListDto.FlatPhotoDto> {
        return client.submitFormWithBinaryData(
            url = "$FLATS_URL/$id/$PHOTOS",
            formData = formData {
                append(
                    key = PHOTO,
                    value = data,
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, contentType)
                        append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                    }
                )
            }
        ).toApiResponse<FlatPhotoListDto.FlatPhotoDto>(json)
    }

    override suspend fun reorderFlatPhotos(
        id: String,
        request: ReorderPhotosRequestDto,
    ): ApiResponse<FlatPhotoListDto> {
        return client.put {
            url("$FLATS_URL/$id/$PHOTOS/$ORDER")
            setBody(request)
        }.toApiResponse<FlatPhotoListDto>(json)
    }

    override suspend fun deleteFlatPhoto(id: String, photoId: String): ApiResponse<Unit> {
        return client.delete {
            url("$FLATS_URL/$id/$PHOTOS/$photoId")
        }.toApiResponse<Unit>(json)
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

    override suspend fun getFlatDocuments(id: String): ApiResponse<FlatDocumentListDto> {
        return client.get {
            url("$FLATS_URL/$id/$DOCUMENTS")
        }.toApiResponse<FlatDocumentListDto>(json)
    }
}
