package org.homesharing.lockbox.domain.model.flat_document

data class FlatDocumentList(
    val items: List<FlatDocument>,
) {
    data class FlatDocument(
        val id: String,
        val docType: FlatDocumentType,
        val fileName: String,
        val contentType: String,
        val sizeBytes: Long,
        val url: String,
        val uploadedAt: String,
    )
}
