package org.homesharing.lockbox.domain.model.contract_version

import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.domain.model.contract_list.ContractDocumentStatus

data class ContractVersion(
    val id: String,
    val contractId: String,
    val number: Int,
    val snapshot: Contract,
    val snapshotSha256: String,
    val documentStatus: ContractDocumentStatus,
    val pdfSha256: String?,
    val downloadUrl: String?,
    val confirmations: List<Contract.Confirmation>,
    val createdAt: String,
)
