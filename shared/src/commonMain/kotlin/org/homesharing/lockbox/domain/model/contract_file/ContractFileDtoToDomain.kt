package org.homesharing.lockbox.domain.model.contract_file

import org.homesharing.lockbox.data.network.dto.contact_file.ContractFileDto
import org.homesharing.lockbox.domain.model.contract_file.DocumentStatus.CREATED
import org.homesharing.lockbox.domain.model.contract_file.DocumentStatus.FAILED
import org.homesharing.lockbox.domain.model.contract_file.DocumentStatus.NONE
import org.homesharing.lockbox.domain.model.contract_file.DocumentStatus.PENDING
import org.homesharing.lockbox.domain.model.contract_file.DocumentStatus.UNKNOWN

internal fun ContractFileDto.toDomain() = ContractFile(
    documentStatus = documentStatus.toDocumentStatus(),
    downloadUrl = downloadUrl,
    id = id
)

private fun String.toDocumentStatus() = when (this) {
    "pending" -> PENDING
    "created" -> CREATED
    "failed" -> FAILED
    "none" -> NONE
    else -> UNKNOWN
}