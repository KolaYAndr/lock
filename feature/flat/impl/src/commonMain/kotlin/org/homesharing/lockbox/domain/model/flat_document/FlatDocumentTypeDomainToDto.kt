package org.homesharing.lockbox.domain.model.flat_document

import org.homesharing.lockbox.data.network.dto.flat_document.FlatDocumentTypeDto

internal fun FlatDocumentType.toDto(): FlatDocumentTypeDto = when (this) {
    FlatDocumentType.EgrnExtract -> FlatDocumentTypeDto.EgrnExtract
    FlatDocumentType.PurchaseAgreement -> FlatDocumentTypeDto.PurchaseAgreement
    FlatDocumentType.OwnershipCertificate -> FlatDocumentTypeDto.OwnershipCertificate
    FlatDocumentType.RentalAgreement -> FlatDocumentTypeDto.RentalAgreement
    FlatDocumentType.Other -> FlatDocumentTypeDto.Other
}
