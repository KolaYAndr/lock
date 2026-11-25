package org.homesharing.lockbox.domain.model.contract_list

import org.homesharing.lockbox.domain.model.ContractStatus
import org.homesharing.lockbox.domain.model.DocumentStatus

data class ContractList(
    val items: List<ContractListItem>,
    val nextCursor: String?,
) {
    data class ContractListItem(
        val id: String,
        val status: ContractStatus?,
        val currentVersionId: String?,
        val city: String,
        val date: String,
        val documentStatus: DocumentStatus,
        val flatAddress: String,
    )
}
