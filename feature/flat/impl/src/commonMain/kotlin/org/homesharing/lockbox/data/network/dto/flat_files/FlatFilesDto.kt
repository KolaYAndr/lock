package org.homesharing.lockbox.data.network.dto.flat_files

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatDetailDto
import org.homesharing.lockbox.domain.model.flat_files.FlatDocument
import org.homesharing.lockbox.domain.model.flat_files.FlatDocumentType

@Serializable
internal data class FlatPhotoListDto(val items: List<FlatDetailDto.PhotoDto>)

@Serializable
internal data class ReorderPhotosRequestDto(@SerialName("photo_ids") val photoIds: List<String>)

@Serializable
internal data class FlatDocumentListDto(val items: List<FlatDocumentDto>)

@Serializable
internal data class FlatDocumentDto(
    val id: String,
    @SerialName("doc_type") val docType: String,
    @SerialName("file_name") val fileName: String,
    @SerialName("content_type") val contentType: String,
    @SerialName("size_bytes") val sizeBytes: Long,
    val url: String,
    @SerialName("uploaded_at") val uploadedAt: String,
)

internal fun FlatDocumentDto.toDomain() = FlatDocument(
    id = id,
    type = FlatDocumentType.entries.firstOrNull { it.apiValue == docType } ?: FlatDocumentType.Other,
    fileName = fileName,
    contentType = contentType,
    sizeBytes = sizeBytes,
    url = url,
    uploadedAt = uploadedAt,
)
