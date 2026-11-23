package org.homesharing.lockbox.data.network.dto.flat_document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class FlatDocumentTypeDto {
    @SerialName("egrn_extract")
    EgrnExtract,

    @SerialName("purchase_agreement")
    PurchaseAgreement,

    @SerialName("ownership_certificate")
    OwnershipCertificate,

    @SerialName("rental_agreement")
    RentalAgreement,

    @SerialName("other")
    Other,
}

internal val FlatDocumentTypeDto.apiValue: String
    get() = FlatDocumentTypeDto.serializer().descriptor.getElementName(ordinal)
