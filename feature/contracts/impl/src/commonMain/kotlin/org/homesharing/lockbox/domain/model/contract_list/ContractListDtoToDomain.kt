package org.homesharing.lockbox.domain.model.contract_list

import org.homesharing.lockbox.data.network.dto.contract_list.ContractDocumentStatusDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractListDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractStatusDto
import org.homesharing.lockbox.domain.model.ContractStatus
import org.homesharing.lockbox.domain.model.DocumentStatus

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

internal fun ContractDocumentStatusDto.toDomain(): DocumentStatus = when (this) {
    ContractDocumentStatusDto.Pending -> DocumentStatus.Pending
    ContractDocumentStatusDto.Created -> DocumentStatus.Created
    ContractDocumentStatusDto.Failed -> DocumentStatus.Failed
    ContractDocumentStatusDto.None -> DocumentStatus.None
}
