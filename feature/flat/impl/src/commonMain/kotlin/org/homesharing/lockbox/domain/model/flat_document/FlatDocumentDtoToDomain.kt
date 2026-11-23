package org.homesharing.lockbox.domain.model.flat_document

import org.homesharing.lockbox.data.network.dto.flat_document.FlatDocumentListDto
import org.homesharing.lockbox.data.network.dto.flat_document.FlatDocumentTypeDto

internal fun FlatDocumentListDto.toDomain() = FlatDocumentList(
    items = items.map { it.toDomain() },
)

internal fun FlatDocumentListDto.FlatDocumentDto.toDomain() =
    FlatDocumentList.FlatDocument(
        id = id,
        docType = docType.toDomain(),
        fileName = fileName,
        contentType = contentType,
        sizeBytes = sizeBytes,
        url = url,
        uploadedAt = uploadedAt,
    )

internal fun FlatDocumentTypeDto.toDomain(): FlatDocumentType = when (this) {
    FlatDocumentTypeDto.EgrnExtract -> FlatDocumentType.EgrnExtract
    FlatDocumentTypeDto.PurchaseAgreement -> FlatDocumentType.PurchaseAgreement
    FlatDocumentTypeDto.OwnershipCertificate -> FlatDocumentType.OwnershipCertificate
    FlatDocumentTypeDto.RentalAgreement -> FlatDocumentType.RentalAgreement
    FlatDocumentTypeDto.Other -> FlatDocumentType.Other
}
