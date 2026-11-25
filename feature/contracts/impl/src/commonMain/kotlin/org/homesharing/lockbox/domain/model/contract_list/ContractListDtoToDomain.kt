package org.homesharing.lockbox.domain.model.contract_list

import org.homesharing.lockbox.data.network.dto.contract_list.ContractDocumentStatusDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractListDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractStatusDto

internal fun ContractListDto.toDomain() = ContractList(
    items = items.map { it.toDomain() },
    nextCursor = nextCursor,
)

internal fun ContractListDto.ContractListItemDto.toDomain() = ContractList.ContractListItem(
    id = id,
    status = status?.toDomain(),
    currentVersionId = currentVersionId,
    city = city,
    date = date,
    documentStatus = documentStatus.toDomain(),
    flatAddress = flatAddress,
)

internal fun ContractStatusDto.toDomain(): ContractStatus = when (this) {
    ContractStatusDto.Draft -> ContractStatus.Draft
    ContractStatusDto.Signing -> ContractStatus.Signing
    ContractStatusDto.Signed -> ContractStatus.Signed
    ContractStatusDto.Rejected -> ContractStatus.Rejected
    ContractStatusDto.Cancelled -> ContractStatus.Cancelled
}

internal fun ContractDocumentStatusDto.toDomain(): ContractDocumentStatus = when (this) {
    ContractDocumentStatusDto.Pending -> ContractDocumentStatus.Pending
    ContractDocumentStatusDto.Created -> ContractDocumentStatus.Created
    ContractDocumentStatusDto.Failed -> ContractDocumentStatus.Failed
    ContractDocumentStatusDto.None -> ContractDocumentStatus.None
}
