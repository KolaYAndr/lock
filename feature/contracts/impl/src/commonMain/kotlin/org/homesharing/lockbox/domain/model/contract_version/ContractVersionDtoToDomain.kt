package org.homesharing.lockbox.domain.model.contract_version

import org.homesharing.lockbox.data.network.dto.contract_version.ContractVersionDto
import org.homesharing.lockbox.data.network.dto.contract_version.ContractVersionListDto
import org.homesharing.lockbox.domain.model.contract.toDomain
import org.homesharing.lockbox.domain.model.contract_list.toDomain

internal fun ContractVersionListDto.toDomain() = ContractVersionList(
    items = items.map { it.toDomain() },
)

internal fun ContractVersionDto.toDomain() = ContractVersion(
    id = id,
    contractId = contractId,
    number = number,
    snapshot = snapshot.toDomain(),
    snapshotSha256 = snapshotSha256,
    documentStatus = documentStatus.toDomain(),
    pdfSha256 = pdfSha256,
    downloadUrl = downloadUrl,
    confirmations = confirmations.map { it.toDomain() },
    createdAt = createdAt,
)
