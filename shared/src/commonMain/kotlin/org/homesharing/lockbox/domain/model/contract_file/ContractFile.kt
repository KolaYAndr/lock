package org.homesharing.lockbox.domain.model.contract_file

data class ContractFile(
    val documentStatus: DocumentStatus,
    val downloadUrl: String,
    val id: String
)

enum class DocumentStatus {
    PENDING,
    CREATED,
    FAILED,
    NONE,
    UNKNOWN
}