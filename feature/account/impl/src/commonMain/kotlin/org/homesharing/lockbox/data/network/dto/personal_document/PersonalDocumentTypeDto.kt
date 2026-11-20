package org.homesharing.lockbox.data.network.dto.personal_document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class PersonalDocumentTypeDto {
    @SerialName("passport")
    Passport,

    @SerialName("additional")
    Additional;
}

internal val PersonalDocumentTypeDto.apiValue: String
    get() = PersonalDocumentTypeDto.serializer().descriptor.getElementName(ordinal)
