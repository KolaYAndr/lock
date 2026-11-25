package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.forms.FormBuilder
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentDisposition
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
import org.homesharing.lockbox.data.network.dto.flat_files.FlatDocumentDto
import org.homesharing.lockbox.data.network.dto.flat_files.FlatDocumentListDto
import org.homesharing.lockbox.data.network.dto.flat_files.FlatPhotoListDto
import org.homesharing.lockbox.data.network.dto.flat_files.ReorderPhotosRequestDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatStatusFilterDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatSummaryListDto
import org.homesharing.lockbox.data.network.dto.flat_list.apiValue

// API endpoints
private const val FLATS = "flats"

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

    override suspend fun archiveFlat(id: String): ApiResponse<FlatDetailDto> =
        client.post { url("$FLATS_URL/$id/archive") }.toApiResponse(json)

    override suspend fun restoreFlat(id: String): ApiResponse<FlatDetailDto> =
        client.post { url("$FLATS_URL/$id/restore") }.toApiResponse(json)

    override suspend fun getPhotos(id: String): ApiResponse<FlatPhotoListDto> =
        client.get { url("$FLATS_URL/$id/photos") }.toApiResponse(json)

    override suspend fun uploadPhoto(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
    ): ApiResponse<FlatDetailDto.PhotoDto> =
        client.submitFormWithBinaryData(
            url = "$FLATS_URL/$id/photos",
            formData = formData { appendFile("photo", data, fileName, contentType) },
        ).toApiResponse(json)

    override suspend fun reorderPhotos(
        id: String,
        photoIds: List<String>,
    ): ApiResponse<FlatPhotoListDto> =
        client.put {
            url("$FLATS_URL/$id/photos/order")
            setBody(ReorderPhotosRequestDto(photoIds))
        }.toApiResponse(json)

    override suspend fun deletePhoto(id: String, photoId: String): ApiResponse<Unit> =
        client.delete { url("$FLATS_URL/$id/photos/$photoId") }.toApiResponse(json)

    override suspend fun getDocuments(id: String): ApiResponse<FlatDocumentListDto> =
        client.get { url("$FLATS_URL/$id/documents") }.toApiResponse(json)

    override suspend fun uploadDocument(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
        documentType: String?,
    ): ApiResponse<FlatDocumentDto> =
        client.submitFormWithBinaryData(
            url = "$FLATS_URL/$id/documents",
            formData = formData {
                appendFile("document", data, fileName, contentType)
                documentType?.let { append("doc_type", it) }
            },
        ).toApiResponse(json)

    override suspend fun deleteDocument(id: String, documentId: String): ApiResponse<Unit> =
        client.delete { url("$FLATS_URL/$id/documents/$documentId") }.toApiResponse(json)

    private fun FormBuilder.appendFile(
        field: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
    ) {
        append(field, data, Headers.build {
            append(HttpHeaders.ContentType, contentType)
            append(HttpHeaders.ContentDisposition, ContentDisposition.File
                .withParameter(ContentDisposition.Parameters.FileName, fileName)
                .toString()
                .substringAfter("; "))
        })
    }
}
