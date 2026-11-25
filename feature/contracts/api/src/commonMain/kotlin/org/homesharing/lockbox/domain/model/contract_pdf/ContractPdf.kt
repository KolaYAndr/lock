package org.homesharing.lockbox.domain.model.contract_pdf

import org.homesharing.lockbox.domain.model.contract_list.ContractDocumentStatus

data class ContractPdf(
    val id: String,
    val documentStatus: ContractDocumentStatus,
    val downloadUrl: String?,
)
