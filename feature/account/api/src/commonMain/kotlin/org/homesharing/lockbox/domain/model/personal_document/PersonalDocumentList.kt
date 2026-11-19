package org.homesharing.lockbox.domain.model.personal_document

data class PersonalDocumentList(
    val items: List<PersonalDocument>,
) {
    data class PersonalDocument(
        val id: String,
        val docType: PersonalDocumentType,
        val fileName: String,
        val contentType: String,
        val sizeBytes: Long,
        val downloadUrl: String,
        val createdAt: String,
    )
}
