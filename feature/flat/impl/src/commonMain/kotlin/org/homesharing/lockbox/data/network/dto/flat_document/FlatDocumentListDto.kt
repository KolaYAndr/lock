package org.homesharing.lockbox.data.network.dto.flat_document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FlatDocumentListDto(
    val items: List<FlatDocumentDto>,
) {
    @Serializable
    internal data class FlatDocumentDto(
        val id: String,
        @SerialName("doc_type")
        val docType: FlatDocumentTypeDto,
        @SerialName("file_name")
        val fileName: String,
        @SerialName("content_type")
        val contentType: String,
        @SerialName("size_bytes")
        val sizeBytes: Long,
        val url: String,
        @SerialName("uploaded_at")
        val uploadedAt: String,
    )
}
