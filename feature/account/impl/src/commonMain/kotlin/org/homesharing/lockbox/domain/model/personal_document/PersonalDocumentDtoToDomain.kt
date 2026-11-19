package org.homesharing.lockbox.domain.model.personal_document

import org.homesharing.lockbox.data.network.dto.personal_document.PersonalDocumentListDto
import org.homesharing.lockbox.data.network.dto.personal_document.PersonalDocumentTypeDto

internal fun PersonalDocumentListDto.toDomain() = PersonalDocumentList(
    items = items.map { it.toDomain() },
)

internal fun PersonalDocumentListDto.PersonalDocumentDto.toDomain() =
    PersonalDocumentList.PersonalDocument(
        id = id,
        docType = docType.toDomain(),
        fileName = fileName,
        contentType = contentType,
        sizeBytes = sizeBytes,
        downloadUrl = downloadUrl,
        createdAt = createdAt,
    )

internal fun PersonalDocumentTypeDto.toDomain(): PersonalDocumentType = when (this) {
    PersonalDocumentTypeDto.Passport -> PersonalDocumentType.Passport
    PersonalDocumentTypeDto.Additional -> PersonalDocumentType.Additional
}
