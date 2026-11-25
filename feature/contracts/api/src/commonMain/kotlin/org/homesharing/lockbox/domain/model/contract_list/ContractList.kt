package org.homesharing.lockbox.domain.model.contract_list

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
        val documentStatus: ContractDocumentStatus,
        val flatAddress: String,
    )
}
