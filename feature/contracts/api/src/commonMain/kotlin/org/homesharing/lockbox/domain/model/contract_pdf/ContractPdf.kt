package org.homesharing.lockbox.domain.model.contract_pdf

import org.homesharing.lockbox.domain.model.DocumentStatus

data class ContractPdf(
    val id: String,
    val documentStatus: DocumentStatus,
    val downloadUrl: String?,
)
