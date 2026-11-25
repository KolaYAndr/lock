package org.homesharing.lockbox.domain.model.flat_files

enum class FlatDocumentType(val apiValue: String) {
    EgrnExtract("egrn_extract"),
    PurchaseAgreement("purchase_agreement"),
    OwnershipCertificate("ownership_certificate"),
    RentalAgreement("rental_agreement"),
    Other("other"),
}

data class FlatDocument(
    val id: String,
    val type: FlatDocumentType,
    val fileName: String,
    val contentType: String,
    val sizeBytes: Long,
    val url: String,
    val uploadedAt: String,
)
