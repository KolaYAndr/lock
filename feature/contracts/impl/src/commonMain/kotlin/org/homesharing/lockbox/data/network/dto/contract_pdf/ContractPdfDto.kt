package org.homesharing.lockbox.data.network.dto.contract_pdf

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.contract_list.ContractDocumentStatusDto

@Serializable
internal data class ContractPdfDto(
    val id: String,
    @SerialName("document_status")
    val documentStatus: ContractDocumentStatusDto,
    @SerialName("download_url")
    val downloadUrl: String?,
)
