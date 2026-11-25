package org.homesharing.lockbox.domain.model.personal_document

import org.homesharing.lockbox.data.network.dto.personal_document.PersonalDocumentTypeDto

internal fun PersonalDocumentType.toDto(): PersonalDocumentTypeDto = when (this) {
    PersonalDocumentType.Passport -> PersonalDocumentTypeDto.Passport
    PersonalDocumentType.Additional -> PersonalDocumentTypeDto.Additional
}
