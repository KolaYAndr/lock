package org.homesharing.lockbox.data.network.dto.personal_document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PersonalDocumentListDto(
    val items: List<PersonalDocumentDto>,
) {
    @Serializable
    internal data class PersonalDocumentDto(
        val id: String,
        @SerialName("doc_type")
        val docType: PersonalDocumentTypeDto,
        @SerialName("file_name")
        val fileName: String,
        @SerialName("content_type")
        val contentType: String,
        @SerialName("size_bytes")
        val sizeBytes: Long,
        @SerialName("download_url")
        val downloadUrl: String,
        @SerialName("created_at")
        val createdAt: String,
    )
}
