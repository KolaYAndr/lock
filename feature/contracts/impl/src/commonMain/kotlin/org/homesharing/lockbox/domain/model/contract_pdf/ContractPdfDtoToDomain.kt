package org.homesharing.lockbox.domain.model.contract_pdf

import org.homesharing.lockbox.data.network.dto.contract_pdf.ContractPdfDto
import org.homesharing.lockbox.domain.model.contract_list.toDomain

internal fun ContractPdfDto.toDomain() = ContractPdf(
    id = id,
    documentStatus = documentStatus.toDomain(),
    downloadUrl = downloadUrl,
)
